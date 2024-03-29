package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.AutorLibroDto;
import com.iter.springboot.apirest.modelo.AutorLibro;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorLibroMapper {

    AutorLibroDto toDto(AutorLibro autorLibro);

    @InheritInverseConfiguration
    AutorLibro toEntity(AutorLibroDto autorLibroDto);

    List<AutorLibroDto> toListDto(List<AutorLibro> autorLibroList);
}
