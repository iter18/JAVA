package com.curso.springboot.horariointerceptor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class AppController {
	
	public String index(Model model) {
		
		model.addAttribute("titulo", "Bienvenido al horario de atención a clientes");
		return "index";
		
	}

}
