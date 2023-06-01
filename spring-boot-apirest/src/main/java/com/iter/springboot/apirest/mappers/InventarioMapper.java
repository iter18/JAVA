package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.modelo.Inventario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMapper {


    @Mapping(source = "precioVenta", target = "precioVenta")
    @Mapping(source = "precioCompra", target = "precioCompra")
    InventarioDto toDto(Inventario inventario);

    @InheritInverseConfiguration
    Inventario toEntity(InventarioDto inventarioDto);

    List<InventarioDto> toListDto(List<Inventario> inventarioList);
}
