package com.curso.springbooterror.controllers;

import com.curso.springbooterror.models.domain.Usuario;
import com.curso.springbooterror.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/index")
    public String index(){
       Integer valor = 100/0;
        //Integer valor = Integer.parseInt("10c");
        return "index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable("id") Integer id, Model model){
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("Titulo", "Detalle usuario:".concat(usuario.getNombre()));
        return "pages/ver";
    }

}
