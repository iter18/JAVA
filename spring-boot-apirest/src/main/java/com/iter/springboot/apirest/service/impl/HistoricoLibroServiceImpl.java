package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.modelo.HistoricoLibro;
import com.iter.springboot.apirest.repository.HistoricoLibroRepository;
import com.iter.springboot.apirest.service.HistoricoLibroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class HistoricoLibroServiceImpl extends AbstractQueryAvanzadoService<HistoricoLibro,Long> implements HistoricoLibroService {

    @Autowired
    private HistoricoLibroRepository historicoLibroRepository;

    @Override
    public JpaSpecificationExecutor<HistoricoLibro> getJpaSpecificationExecutor() {
        return historicoLibroRepository;
    }

    @Override
    public JpaRepository<HistoricoLibro, Long> getJpaRepository() {
        return historicoLibroRepository;
    }

    @Override
    @Transactional
    public HistoricoLibro alta(HistoricoLibro historicoLibro) {
        return historicoLibroRepository.save(historicoLibro);
    }
}
