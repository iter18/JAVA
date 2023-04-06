package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.dtos.ComboDto;

import java.util.List;

public interface AutorService {

    AutorDto alta(AutorDto autorDto);

    List<AutorDto> buscar();

    List<AutorDto>buscar(String nombre);

    AutorDto buscar(Long id);

    AutorDto modificar(Long id, AutorDto autorDto);

    void eliminar(Long id);

    List<ComboDto> buscarC();
}
