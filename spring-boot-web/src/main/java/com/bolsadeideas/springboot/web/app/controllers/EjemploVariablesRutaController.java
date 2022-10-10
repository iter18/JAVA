package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/variables")
public class EjemploVariablesRutaController {

	/**
	 * la anotación PathVairable se usa para pasar parametros atraves de las anotaciones o handlers de los metodos
	 */
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "Enviar más de 1 parámetros del @PathVariable GET - URL");
		return "variables/index";
	}
	@GetMapping("/string/{texto}")
	/**esta forma es para indicarle al metodo que aunque se llame diferente la variable de Path tenemos que relacionarla con el nombre exacto
	 * que se define en la anotación(handler)
	 */
	//public String variables(@PathVariable(name = "texto") String textoM, Model model) {
	
	//Y esta forma nos saltamos el alias y lo pasamos directo al metodo pero la variable de Path es la misma que la del GetMapping
	public String variables(@PathVariable String texto, Model model) {
		
		model.addAttribute("titulo", "Recibir parámetro del la ruta(@PathVariable) GET - URL");
		model.addAttribute("resultado", "El texto recibido mediante @PatVariable es: '"+texto+"'");
		
		return "variables/ver";
	}
	/**
	 * Recibir más de una varaible o parametro con PathVariable
	 * Sobreescribimos el metodo variables
	 */
	
	@GetMapping("/string/{texto}/{numero}")
	public String variables(@PathVariable String texto, @PathVariable Integer numero, Model model ) {
		model.addAttribute("titulo", "Recibir más de 1 parámetro del la ruta(@PathVariable) GET - URL");
		model.addAttribute("resultado", "El texto recibido mediante @PatVariable es: '"+texto+"' y el número recibido es: "+numero);
		return "variables/ver";
	}
}
