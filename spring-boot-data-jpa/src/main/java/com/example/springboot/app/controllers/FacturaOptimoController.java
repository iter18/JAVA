package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.models.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/facturaOptimo")
@Slf4j
@SessionAttributes("invoice")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class FacturaOptimoController {
    @Autowired
    ClienteOptimoService clienteService;

    //Método para ver el detalle de la factura
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Long id,
                      Model model,
                      RedirectAttributes flash){

        Invoice factura = clienteService.buscar(id);

        if(factura == null){
            flash.addFlashAttribute("error","La factura no existe, verifique e intente nuevamente");
            return "redirect:/cliente/listar";
        }
        model.addAttribute("factura",factura);
        model.addAttribute("titulo","Factua: ".concat(factura.getDescripcion()));

        return "factura/ver";
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable("clienteId") Long clienteId,
                        Map<String,Object> model,
                        RedirectAttributes flash){
        Invoice factura = clienteService.crearFactura(clienteId);
        if(factura == null){
            flash.addFlashAttribute("error","El cliente seleccionado no existe");
            return "redirect:/listar";
        }
        model.put("invoice",factura);
        model.put("titulo","Crear Factura");
        return "factura/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable("term") String term){

        List<Producto> productoList = clienteService.buscar(term);
        return productoList;
    }
    //Método funcional sin buenas practicas, otra manera de hacerlo
    @PostMapping(value="/form")
    public String guardar(@Valid Invoice factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required = false)List<Long> itemId,
                          @RequestParam(name = "cantidad[]", required = false)List<Integer> cantidad,
                          RedirectAttributes flash,
                          SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo", "Crear Factura");
            return "factura/form";
        }
        if(itemId == null || itemId.size() == 0){
            model.addAttribute("titulo", "Crear Factura");
            model.addAttribute("error", "Error: La factura debe tener items");
            return "factura/form";
        }
        clienteService.saveFactura(factura,itemId,cantidad);
        status.setComplete();

        flash.addFlashAttribute("success","Factura creada con éxito!");

        return "redirect:/cliente/ver/"+factura.getCliente().getId();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id")Long id,RedirectAttributes flash){
        Invoice factura = clienteService.eliminarFactura(id);
        if(factura!=null){
            flash.addFlashAttribute("success","Factura eliminada con éxito!");
            return "redirect:/cliente/ver/"+factura.getCliente().getId();
        }
        flash.addFlashAttribute("error","La factura no existe, verifique e intente nuevamente");
        return "redirect:/cliente/listar";
    }
}
