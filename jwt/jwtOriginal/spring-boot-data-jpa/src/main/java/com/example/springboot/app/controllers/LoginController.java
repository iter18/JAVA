package com.example.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false)String error,
                        Model model,
                        Principal principal,
                        RedirectAttributes flash){
        if(principal!=null){
            flash.addFlashAttribute("info","Ya has iniciado sesión anteriormente");
            return "redirect:/cliente/";
        }
        if(error!=null){
            model.addAttribute("error","Operación no permitida: Los datos proporcionados del usuario no existen");
        }
        return "login";
    }
}
