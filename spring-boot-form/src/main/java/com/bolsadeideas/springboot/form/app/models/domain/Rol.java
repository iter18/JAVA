package com.bolsadeideas.springboot.form.app.models.domain;

import org.springframework.expression.spel.ast.OperatorInstanceof;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rol {
	
	private Integer id;
	private String nombre;
	private String rol;
	@Override
	public boolean equals(Object obj) {
		
		//Que el objeto sea del tipo del mismo objeto
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Rol)) {
			return false;
		}
		Rol rol = (Rol) obj;
		
		return this.id!=null&& this.id.equals(rol.getId());
	}
	
	

}
