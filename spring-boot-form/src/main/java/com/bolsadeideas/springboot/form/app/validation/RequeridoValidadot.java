package com.bolsadeideas.springboot.form.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class RequeridoValidadot implements ConstraintValidator<Requerido, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		/*
		 * Validamos si esta vacio o en blanco
		 */
		//if(value == null||value.isEmpty() || value.isBlank()) {
		//tambien puede ser así con la utileria de Spring
		if(value == null||!StringUtils.hasText(value)){
			return false;
		}
		return true;
	}

}
