package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.mappers.AutorMapper;
import com.iter.springboot.apirest.modelo.Autor;
import com.iter.springboot.apirest.repository.AutorRepository;
import com.iter.springboot.apirest.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    AutorMapper autorMapper;
    @Override
    public AutorDto alta(AutorDto autorDto) {
        Autor autor = autorMapper.toEntity(autorDto);

        return autorMapper.toDto(autorRepository.save(autor));
    }

    @Override
    public List<AutorDto> buscar() {

        return autorMapper.toListDto(autorRepository.findAll());
    }
}
