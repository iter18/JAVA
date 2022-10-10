package com.bolsadeideas.springboot.form.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordRegexValidador implements ConstraintValidator<PasswordRegex, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value.matches("[A-Z$&+,:;=?@#|'<>.^*()%!0-9a-z]{8}")) 
			return true;
		return false;
	}

}
