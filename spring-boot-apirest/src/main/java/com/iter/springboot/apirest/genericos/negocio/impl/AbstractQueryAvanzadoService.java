package com.iter.springboot.apirest.genericos.negocio.impl;

import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.genericos.negocio.QueryService;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public abstract class AbstractQueryAvanzadoService<E,K> extends AbstractQueryService<E,K> implements QueryService<E,K>, QueryAvanzadoService<E,K> {


    public AbstractQueryAvanzadoService() {
    }

    public List<E> buscar(Specification specification) {
        return this.getJpaSpecificationExecutor().findAll(specification);
    }


    public Optional<E> buscarUnico(Specification specification) {
        return this.getJpaSpecificationExecutor().findOne(specification);
    }
}
