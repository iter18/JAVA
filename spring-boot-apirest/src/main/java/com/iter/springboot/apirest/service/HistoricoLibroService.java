package com.iter.springboot.apirest.service;

import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.modelo.HistoricoLibro;

import java.util.List;

public interface HistoricoLibroService extends QueryAvanzadoService<HistoricoLibro,Long> {

    HistoricoLibro alta(HistoricoLibro historicoLibro);

    List<HistoricoLibro> buscar(Long idLibro);


}
