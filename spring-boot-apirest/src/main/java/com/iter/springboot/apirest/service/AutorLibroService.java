package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.modelo.AutorLibro;

import java.util.List;
import java.util.Optional;

public interface AutorLibroService {

    AutorLibro alta(AutorLibro autorLibro);

    List<AutorLibro> buscar(String isbn, String titulo, Long autorId);

    AutorLibro modificar(AutorLibro autorLibro);

    Optional<AutorLibro> buscarPorId(Long id);

    void eliminar(AutorLibro autorLibro);

}
