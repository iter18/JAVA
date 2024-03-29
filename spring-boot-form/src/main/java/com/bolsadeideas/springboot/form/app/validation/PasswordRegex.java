package com.bolsadeideas.springboot.form.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PasswordRegexValidador.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface PasswordRegex {
	String message() default "Contraseña Invalida";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };

}
