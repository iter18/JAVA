package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.AutorDto;

import java.util.List;

public interface AutorService {

    AutorDto alta(AutorDto autorDto);

    List<AutorDto> buscar();
}
