package com.bolsadeideas.springboot.di.app.models.domain;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Component
@RequestScope //sireve para instanciar más de una vez el componente es para multiusuarios al mismo tiempo, poco se usa
//@SessionScope// sireve para persistir el componente en este caso en la sessión del navegador, la hacerlo de session ya no es necesario ejecutar @PostDestroy
public class Factura implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4368254314188119877L;

	@Value("${factura.descripcion}")
	String descripcion;
	
	@Autowired
	private List<ItemFactura> items;

	@Autowired
	private Cliente cliente;
	
	/**
	 * @PostConstruct: sirve para realizar procesos antes una vez que se haya inyectado las dependencias
	 */
	@PostConstruct
	public void inicializar() {
		cliente.setNombre(cliente.getNombre().concat(" ").concat("Antonio"));
		descripcion = descripcion.concat(" del cliente: ").concat(cliente.getApellido());
	}
	
	/**
	 * @PreDestroy: sirve para realizar un proceso antes de matar el bean o componente
	 */
	@PreDestroy
	public void destruir() {
		System.out.println("Factura destruida: ".concat(descripcion));
	}

}
