package com.bolsadeideas.springboot.di.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsadeideas.springboot.di.app.models.service.IServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicio;

/**
 * 
 * @author Home B
 *
 */

@Controller   
public class IndexController {
	/*@Autowired Manera 1 hacerlo por tipo especifico de clase aunque se tendría que definir por cada proceso una nueva clase si queremos cambiar el comportamiento del servicio 
	LA forma 1 es la más comun
	*/
	
	//@Quilifier decide que proceso ejecutar
	@Autowired
	@Qualifier("miServicioComplejo")
	private IServicio servicio;
	//Manera 2 con la interfaz definimos el comportamiento que tendra la operación por medio del servicio
	/*@Autowired
	private IServicio servicio;*/
	
	/*MAenra 3 de inyectar por Setter, dfinimos la interfaz y en metodo setter colocamos la anotación @Autowired para indicarle a la interfaz que se va definir el metodo por medio de setter
	private IServicio servicio;*/
	//Manera 4 Inyección por Contructor abajo de la declaración de la interfaz colocamos el ocontructor
	/*
	@Autowired
	public IndexController(IServicio servicio) {
		this.servicio = servicio;
	}*/
	@GetMapping({"/","","/index"})
	public String index(Model model) {
		model.addAttribute("titulo", "Valor desde Servicio");
		model.addAttribute("objeto", servicio.operacion());
		return "index";
	}
	//Forma 3 Se coloca la anotación aquí para que spring encuentre la inyeción
	/*@Autowired
	public void setServicio(IServicio servicio) {
		this.servicio = servicio;
	}*/

	
	
	
}
