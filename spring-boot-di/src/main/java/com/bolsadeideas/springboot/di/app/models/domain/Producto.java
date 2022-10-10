package com.bolsadeideas.springboot.di.app.models.domain;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Producto {
	private String nombre;
	private Integer precio;
	public Producto(String nombre, Integer precio) {

		this.nombre = nombre;
		this.precio = precio;
	}
	
	

}
