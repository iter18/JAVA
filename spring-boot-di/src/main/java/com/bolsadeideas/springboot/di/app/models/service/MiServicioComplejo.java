package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Primary indicamos que este servicio se ejecutara primero que todos
//vamos a documentar las anotaciones por que se inyectan en la clase APPconfig y ya no es necesario
//@Primary
//@Component("miServicioComplejo")
public class MiServicioComplejo implements IServicio {
	
	public String operacion() {
		return "Ejecutando proceso complejo ....";
	}

}
  