package com.bolsadeideas.springboot.di.app.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class ItemFactura {
	public ItemFactura(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	private Producto producto;
	private Integer cantidad;
	
	public Integer calculaPrecio() {
		return cantidad * producto.getPrecio();
	}
}
