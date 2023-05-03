package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.modelo.Movimiento;
import com.iter.springboot.apirest.repository.MovimientoRepository;
import com.iter.springboot.apirest.service.MovimientoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovimientoServiceImpl extends AbstractQueryAvanzadoService<Movimiento,Long> implements MovimientoService {

    @Autowired
    MovimientoRepository movimientoRepository;

    @Override
    public JpaSpecificationExecutor<Movimiento> getJpaSpecificationExecutor() {
        return movimientoRepository;
    }

    @Override
    public JpaRepository<Movimiento, Long> getJpaRepository() {
        return movimientoRepository;
    }
}
