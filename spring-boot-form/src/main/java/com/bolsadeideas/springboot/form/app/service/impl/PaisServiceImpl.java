package com.bolsadeideas.springboot.form.app.service.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.service.PaisService;


@Service
public class PaisServiceImpl implements PaisService{
	
	private List<Pais> lista;
	

	public PaisServiceImpl() {
		lista = Arrays.asList(
				new Pais(1,"México","MX"),
				new Pais(2,"España","ES"),
				new Pais(3,"Chile","CL"),
				new Pais(4,"Perú","PE"),
				new Pais(5,"Colombia","CO"),
				new Pais(6,"Argentina","ARG"));
	}

	@Override
	public List<Pais> listar() {
		
		return lista;
	}

	@Override
	public Pais buscarById(Integer id) {
		//JAVA 7 o menos
		Pais pais = null;
		
		for(Pais p : lista) {
			if(p.getId() == id) {
				pais = p;
				break;
			}
			
		}
		//JAVA 8 +
		/*
		pais = lista.stream().map(p->{
			if(p.getId().equals(id)) {
				pais = p;
			}
		}).collect(Collectors.toList());*/
		
		return pais;
	}

}
