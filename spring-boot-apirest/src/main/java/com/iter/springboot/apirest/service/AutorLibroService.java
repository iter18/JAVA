package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.modelo.AutorLibro;

import java.util.List;
import java.util.Optional;

public interface AutorLibroService {

    AutorLibro alta(AutorLibro autorLibro);

    List<AutorLibro> buscar();

    AutorLibro modificar(AutorLibro autorLibro);

    Optional<AutorLibro> buscarPorId(Long id);

}
