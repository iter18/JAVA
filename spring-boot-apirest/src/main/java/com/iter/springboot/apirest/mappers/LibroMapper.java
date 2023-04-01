package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.LibroDto;
import com.iter.springboot.apirest.modelo.Libro;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {


    LibroDto toDto(Libro libro);


    @InheritInverseConfiguration
    Libro toEntity(LibroDto libroDto);

    List<LibroDto> toListDto(List<Libro> libroList);

}
