package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.mappers.AutorMapper;
import com.iter.springboot.apirest.modelo.Autor;
import com.iter.springboot.apirest.repository.AutorRepository;
import com.iter.springboot.apirest.repository.specification.AutorSpecification;
import com.iter.springboot.apirest.service.AutorService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AutorServiceImpl implements AutorService {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    AutorMapper autorMapper;
    @Override
    @Transactional
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

    @Override
    public List<AutorDto> buscar(String nombre) {
        Specification<Autor> filtro = AutorSpecification.likeNombre(nombre);
        return autorMapper.toListDto(autorRepository.findAll(filtro));
    }

    @Override
    public AutorDto buscar(Long id) {
        return autorMapper.toDto(autorRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No se encotro el autor")));
    }

    @Override
    @Transactional
    public AutorDto modificar(Long id, AutorDto autorDto) {
            Assert.hasText(autorDto.getNombre(),"Campo nombre es requerido");
            Assert.hasText(autorDto.getApellido(), "Campo apellido es requerido");
            Autor autor = autorRepository.findById(id)
                    .orElseThrow(()->new IllegalArgumentException("No se encotro el autor"));

            autor.setNombre(autorDto.getNombre());
            autor.setApellido(autorDto.getApellido());

            return autorMapper.toDto(autorRepository.saveAndFlush(autor));

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("El registro a eliminar no existe"));
        autorRepository.delete(autor);
    }
}
