package com.bolsadeideas.springboot.form.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Rol;
import com.bolsadeideas.springboot.form.app.service.RolService;

@Service
public class RolServiceImpl implements RolService {

	List<Rol> lista;
	
	
	
	public RolServiceImpl() {
		lista = Arrays.asList(
				new Rol(1,"ROLE_ADMIN","Administrador"),
				new Rol(2,"ROLE_USER","Usuario"),
				new Rol(3,"ROLE_MODERATOR","Moderador")
				);
	}

	@Override
	public List<Rol> listar() {
		return lista;
	}

	@Override
	public Rol buscarById(Integer id) {

		Rol rol = null;
		
		//JAVA 7 o menos
		for(Rol r : lista) {
			if(r.getId() == id) {
				rol = r;
				break;
			}
			
		}
		
		return rol;
	}

	@Override
	public List<Rol> buscarById(List<Integer> listId) {
		
		//JAVA 8+ iterar un list
		List<Rol> roles = listId.stream().map(m->this.buscarById(m)).collect(Collectors.toList());
	
		return roles;

	}

}
