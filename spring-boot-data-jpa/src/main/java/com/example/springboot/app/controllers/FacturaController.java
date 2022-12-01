package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    ClienteService clienteService;

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
}
