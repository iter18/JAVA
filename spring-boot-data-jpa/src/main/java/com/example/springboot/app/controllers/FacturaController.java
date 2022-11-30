package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
