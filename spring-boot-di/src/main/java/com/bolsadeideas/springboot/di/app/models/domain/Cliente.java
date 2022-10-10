package com.bolsadeideas.springboot.di.app.models.domain;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
//@SessionScope
@RequestScope
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8336014449752350612L;
	@Value("${cliente.nombre}")
	 String nombre;
	@Value("${cliente.apellido}")
	 String apellido;
}
