package com.bolsadeideas.springboot.web.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/params")
public class EjemploParamsController {

	//metodo para pasar parametros por URL
	// En el RequestParam se especifica el parametro que va llegar y se le coloca un argumento más como required =false y un valor por defecto para que no truene
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "Enviar parámetros del Request HTTP GET - URL");
		return "params/index";
	}
	
	@GetMapping("/string")
	public String param(@RequestParam(name="texto", required = false, defaultValue = "Esperando parametro ...") String texto, Model model) {
		
		model.addAttribute("titulo", "Recibir parámetro del Request HTTP GET - URL");
		model.addAttribute("resultado", "El texto enviado es: "+texto);
		return "params/ver";
	}
	
	/**
	 * Forma1 de Recibir mas de un solo tipo de parámetro haciendo una sobre carga de metodo
	 */
	@GetMapping("/mix-params")
	public String param(@RequestParam String saludo, @RequestParam Integer numero, Model model) {
		model.addAttribute("titulo","Recibir más de 2 tipos de parámetros con Request HTTP GET - URL");
		model.addAttribute("resultado", "El saludo recibido es: '"+saludo+"' y el número es: '"+numero+"'");
		return "params/ver";
	}
	
	/**
	 * Forma 2 de Recibir parametros por HttpServletRequest, se especifican dentro del metodo los tipos de parametros y sobrecargamos el metodo nuevamente
	 */
	@GetMapping("/mix-params-servletRequest")
	public String param(HttpServletRequest request, Model model) {
		
		String saludo = request.getParameter("saludo");
		Integer numero = null;
		//validamos si es o no entero
		try {
			numero = Integer.parseInt(request.getParameter("numero"));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			numero = 0;
		}
		model.addAttribute("titulo","Recibir más de 2 tipos de parámetros con HttpServletRequest HTTP GET - URL");
		model.addAttribute("resultado", "El saludo recibido es: '"+saludo+"' y el número es: '"+numero+"'");
		return "params/ver";
	}
}
