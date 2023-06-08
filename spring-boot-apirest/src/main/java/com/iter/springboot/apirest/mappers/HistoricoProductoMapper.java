package com.iter.springboot.apirest.mappers;

import com.iter.springboot.apirest.dtos.HistoricoProductoDto;
import com.iter.springboot.apirest.modelo.HistoricoLibro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoricoProductoMapper {

  @Mapping(source = "movimiento.tipoMovimiento", target = "movimiento")
  HistoricoProductoDto toDto(HistoricoLibro historicoLibro);

  List<HistoricoProductoDto> toListDto(List<HistoricoLibro> historicoLibroList);
}
