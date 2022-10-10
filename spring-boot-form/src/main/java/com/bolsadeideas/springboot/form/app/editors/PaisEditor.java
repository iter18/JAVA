package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.service.PaisService;

@Component
public class PaisEditor extends PropertyEditorSupport {
	
	@Autowired
	private PaisService paisService;

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
			try {
				
				Integer idPais = Integer.parseInt(id);
				setValue(paisService.buscarById(idPais));
				
			} catch (NumberFormatException e) {
				setValue(null);
			}
			
	}
	
	

}
