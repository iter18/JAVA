package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.form.app.validation.PasswordRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	
	String identificador;
	
	@NotEmpty
	String nombre;
	
	@NotEmpty
	String apellido;
	
	//@NotEmpty
	//@NotBlank valida que no este vacio y que tenga espacios en blanco pero sólo para strings
	@NotBlank
	String username;
	/*
	 * Diferentes formas de validar un objeto:
	 * 	NOTA1: (Un objeto puede ser Un entity,bean, Integer,Date,Long,Boolean etc.)
	 *  @NotNull se usa para validar objetos unicamente es decir valores primitivos no
	 *  @Min(1) se usa para validar un valor primitivo y objetos en cuanto a valor y esta indicando que minimo debe tener un valor de uno
	 *  @Max(5) se usa para validar un primitivo u objeto en cuanto a valor y esta indicando que debe tener un valor de 5
	 *  Nota2: Un valor primitivo int,boolean,float
	 */
	@NotNull
	@Min(2)
	@Max(5)
	Integer cuenta;
	
	//@Size de javax Validation sirve para darle longitud
	//@Patern de javax VAlidation sirve para crear una expresion regular y validar en base a una estructura, pero siempre debe de especificar la longitud 
	@NotEmpty
	//@Pattern(regexp = "[A-Z$&+,:;=?@#|'<>.^*()%!0-9a-z]{8}")
	@Size(min = 3, max=8)
	@PasswordRegex
	String password;
	
	@Requerido
	@Email
	String email;
	
	//@future Valida fecha despues de la del presente
	//@Past Valida fecha sea pasada a la del presente
	@NotNull
	@Past
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	Date fechaNacimiento;
	
	@NotNull
	private Pais pais;
	
	/*@Valid
	private Pais pais;*/
	
	/*@NotEmpty
	private List<String> roles;*/
	
	@NotNull
	private List<Rol> roles;
	
	private Boolean habilitar;
	
	@NotEmpty
	private String genero;
	
	@NotEmpty
	private String valorSecreto;
}
