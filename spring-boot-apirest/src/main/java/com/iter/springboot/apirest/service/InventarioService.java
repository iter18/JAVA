package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.dtos.AltaProductoInventarioDto;
import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.modelo.Inventario;

public interface InventarioService extends QueryAvanzadoService<Inventario,Long> {

    InventarioDto altaProducto(AltaProductoInventarioDto altaProductoInventarioDto);
}
