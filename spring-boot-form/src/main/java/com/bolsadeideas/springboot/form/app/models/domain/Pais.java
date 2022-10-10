package com.bolsadeideas.springboot.form.app.models.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pais {
	//@NotNull
	 private Integer id;
	 private String nombre;
	 private String codigo;
	 //Manera de hacerlo directamente sin buscarlo con service
	@Override
	public String toString() {
		return this.id.toString();
	}

	 
}
