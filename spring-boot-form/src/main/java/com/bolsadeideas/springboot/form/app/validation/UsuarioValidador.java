package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

//@Component es para indicar a spring que se va a poder inyectar el componenente
@Component
public class UsuarioValidador implements Validator {

	//Este metodo simplemente es para asignar al entity que vamos a validar 
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	//Aquí especificamos las validaciones, donde Object se refiere al objeto a validar y errors los mensajes
	@Override
	public void validate(Object target, Errors errors) {
		//Usuario usuario = (Usuario)target;
		
		/*
		 * Forma de hacerlo con ValidationUtils de Spring
		 * ValidationUtils.rejectIfEmpty es un if fabricado de Validator en una linea
		 * rejectIfEmptyOrWhitespace es para validar que no este vacio y tenga espacios vacios
		 * @Params: 
		 * 	errors -> para lanzar los mensajes
		 *  campo del entity, debe ser igual como se declaro en el bean
		 *  mensaje del archivo propiedad para búscar el mensaje de error a mostrar
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.usuario.username");
		
		//La otra forma de hacerlo es de manera tradicional con IF sin usar VAlidationUtils
		/*if(usuario.getUsername().isEmpty()) {
			errors.rejectValue("nombre", "NotEmpty.usuario.username");
		}*/
		/*if(!usuario.getPassword().matches("[A-Z$&+,:;=?@#|'<>.^*()%!0-9a-z]{8}")) {
			errors.rejectValue("password", "usuario.password.error");
		}*/
		
	}

}
