package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.AltaProductoInventarioDto;
import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.modelo.Inventario;

import java.util.List;

public interface InventarioService extends QueryAvanzadoService<Inventario,Long> {


    List<InventarioDto> buscar(String isbn, String titulo);
    InventarioDto altaProducto(AltaProductoInventarioDto altaProductoInventarioDto);


}
