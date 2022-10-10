package com.bolsadeideas.springboot.form.app.service;

import java.util.List;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

public interface PaisService {
	
	List<Pais> listar();
	
	Pais buscarById(Integer id);

}
