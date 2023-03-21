package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.mappers.AutorMapper;
import com.iter.springboot.apirest.modelo.Autor;
import com.iter.springboot.apirest.repository.AutorRepository;
import com.iter.springboot.apirest.repository.specification.AutorSpecification;
import com.iter.springboot.apirest.service.AutorService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    AutorMapper autorMapper;
    @Override
    public AutorDto alta(AutorDto autorDto) {
        Assert.hasText(autorDto.getNombre(),"Campo nombre es requerido");
        Assert.hasText(autorDto.getApellido(), "Campo apellido es requerido");

        Specification<Autor> filtro = AutorSpecification.nombre(autorDto.getNombre())
                .and(AutorSpecification.apellido(autorDto.getApellido()));
        Optional<Autor> registroExiste = autorRepository.findOne(filtro);
        if(registroExiste.isPresent()){
           throw new IllegalArgumentException("El registro para este autor ya existe, intente con uno distinto");
        }
        Autor autor = autorMapper.toEntity(autorDto);
        return autorMapper.toDto(autorRepository.save(autor));
    }

    @Override
    public List<AutorDto> buscar() {

        return autorMapper.toListDto(autorRepository.findAll());
    }
}
