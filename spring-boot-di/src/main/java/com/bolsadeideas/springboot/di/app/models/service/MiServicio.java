package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Home B
 *
 * @Component == @Servicio sólo cambia la definición de servicio pero funciona igual
 * 
 * el servicio va definir el comportamiento de la interfaz, de esta menera podemos tener varios servicios dfiniendo el metodo operacipion
 * vamos a documentar las anotaciones por que se inyectan en la clase APPconfig y ya no es necesari
 */

//@Component("miServicioSimple")
public class MiServicio implements IServicio {
	
	public String operacion() {
		return "Ejecutando algúna proceso importante...";
	}

}
