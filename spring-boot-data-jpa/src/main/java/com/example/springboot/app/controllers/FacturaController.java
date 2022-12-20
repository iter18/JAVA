package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.ItemFactura;
import com.example.springboot.app.models.entity.Producto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/factura")
@Slf4j
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    ClienteService clienteService;

    //Método para ver el detalle de la factura
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Long id,
                      Model model,
                      RedirectAttributes flash){

        Factura factura = clienteService.buscar(id);

        if(factura == null){
            flash.addFlashAttribute("error","La factura no existe, verifique e intente nuevamente");
            return "redirect:/listar";
        }
        model.addAttribute("factura",factura);
        model.addAttribute("titulo","Factua: ".concat(factura.getDescripcion()));

        return "factura/ver";
    }

  @GetMapping("/form/{clienteId}")
  public String crear(@PathVariable("clienteId") Long clienteId,
                      Map<String,Object> model,
                      RedirectAttributes flash){
      Cliente cliente = clienteService.findById(clienteId);
      if(cliente == null){
          flash.addFlashAttribute("error","El cliente seleccionado no existe");
          return "redirect:/listar";
      }
      Factura factura = new Factura();
      factura.setCliente(cliente);
      model.put("factura",factura);
      model.put("titulo","Crear Factura");
      return "factura/form";
  }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody  List<Producto> cargarProductos(@PathVariable("term") String term){

        List<Producto> productoList = clienteService.buscar(term);
        return productoList;
    }
    //Método funcional sin buenas practicas, otra manera de hacerlo
    @PostMapping(value="/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required = false)Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false)Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status){
      if(result.hasErrors()){
          model.addAttribute("titulo", "Crear Factura");
          return "factura/form";
      }
      if(itemId == null || itemId.length == 0){
          model.addAttribute("titulo", "Crear Factura");
          model.addAttribute("error", "Error: La factura debe tener items");
          return "factura/form";
      }

        //Forma 1
        for (int i =0; i< itemId.length; i++){
            Producto producto = clienteService.buscarProductoBy(itemId[i]);

            ItemFactura item = new ItemFactura();
            //item.setId(Long.parseLong("1"));
            item.setCantidad(cantidad[i]);
            item.setProducto(producto);
            factura.addItemsFactura(item);

            log.info("ID: "+ itemId[i].toString() + ", cantidad: "+ cantidad[i].toString());
        }
        clienteService.saveFactura(factura);
        status.setComplete();

        flash.addFlashAttribute("success","Factura creada con éxito!");

        return "redirect:/ver/"+factura.getCliente().getId();
    }
}
