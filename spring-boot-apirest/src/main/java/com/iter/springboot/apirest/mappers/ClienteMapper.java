package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.ClienteDto;
import com.iter.springboot.apirest.modelo.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "fechaCreacion", source = "createAt")
    ClienteDto toDto(Cliente cliente);


}
