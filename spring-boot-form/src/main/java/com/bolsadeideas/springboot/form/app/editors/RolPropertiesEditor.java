package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.service.RolService;

@Component
public class RolPropertiesEditor extends PropertyEditorSupport {

	@Autowired
	private RolService rolService;
	
	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		
		try {
			Integer idRol = Integer.parseInt(id);
			
			setValue(rolService.buscarById(idRol));
			
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}

	
}
