package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.modelo.Inventario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    InventarioDto toDto(Inventario inventario);

    @InheritInverseConfiguration
    Inventario toEntity(InventarioDto inventarioDto);

    List<InventarioDto> toListDto(List<Inventario> inventarioList);
}
