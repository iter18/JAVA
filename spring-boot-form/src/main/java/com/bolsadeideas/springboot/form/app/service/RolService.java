package com.bolsadeideas.springboot.form.app.service;

import java.util.List;

import com.bolsadeideas.springboot.form.app.models.domain.Rol;

public interface RolService {
	
	List<Rol> listar();
	
	Rol buscarById(Integer id);
	
	List<Rol> buscarById(List<Integer> listId);

}
