package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.AutorDto;
import com.iter.springboot.apirest.modelo.Autor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDto toDto(Autor autor);

    Autor toEntity(AutorDto autorDto);

    List<AutorDto> toListDto(List<Autor> autorList);
}
