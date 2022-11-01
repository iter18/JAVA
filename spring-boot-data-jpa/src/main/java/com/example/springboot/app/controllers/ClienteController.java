package com.example.springboot.app.controllers;

import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value="/listar")
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clienteService.findAll());

        return "listar";
    }

    @GetMapping(value = "/form")
    public String crear(Map<String,Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario cliente");
        return "form";
    }

    @PostMapping(value="/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("titulo","Formulario cliente");
            return "form";
        }
        String mensaje = cliente.getId()!=null ? "Cliente editado con éxisto" : "Cliente creado con éxito!";
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:listar";
    }

    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable("id") Long id, Map<String,Object> model,RedirectAttributes flash){
        Cliente cliente = null;
        if(id>0){
            flash.addFlashAttribute("error","El ID del cliente no existe en la BD!");
            cliente = clienteService.findById(id);
        }else{
            flash.addFlashAttribute("error","El ID del cliente no puede ser cero!");
            return "redirect:/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Editar cliente");
        return "form";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,RedirectAttributes flash){
        Cliente cliente = null;
        if(id>0){
            clienteService.delete(id);
            flash.addFlashAttribute("info","Cliente eliminado con éxito!");
        }

        return "redirect:/listar";
    }
}
