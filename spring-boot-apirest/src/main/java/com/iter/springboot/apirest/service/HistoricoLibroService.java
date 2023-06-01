package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.modelo.HistoricoLibro;

public interface HistoricoLibroService extends QueryAvanzadoService<HistoricoLibro,Long> {

    HistoricoLibro alta(HistoricoLibro historicoLibro);
}
