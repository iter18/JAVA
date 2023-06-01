package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.modelo.Kardex;

public interface KardexService extends QueryAvanzadoService<Kardex,Long> {

    Kardex alta(Kardex kardex);

    Kardex modificar(Kardex kardex);
}
