package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//Clase controller para especificar pagina de inicio
@Controller
public class HomeController {

	
	@GetMapping("/")
	public String home() {
		//primera manera con redirect pero carga todo los metodos de cero 
		//return "redirect:/app/index";
		//sgunda manera con forward pero no carfa todos los metodos es mejor este manera si se quiere establecer una pagina inicial
		return "forward:/app/index";
	}
}
