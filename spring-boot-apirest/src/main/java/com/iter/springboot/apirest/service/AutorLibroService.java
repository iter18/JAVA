package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.modelo.AutorLibro;

import java.util.List;

public interface AutorLibroService {

    AutorLibro alta(AutorLibro autorLibro);

    List<AutorLibro> buscar();

}
