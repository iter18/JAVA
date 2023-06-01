package com.iter.springboot.apirest.genericos.negocio.impl;

import com.iter.springboot.apirest.genericos.negocio.QueryAvanzadoService;
import com.iter.springboot.apirest.genericos.negocio.QueryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public abstract class AbstractQueryAvanzadoService<E,K> extends AbstractQueryService<E,K> implements QueryService<E,K>, QueryAvanzadoService<E,K> {


    public AbstractQueryAvanzadoService() {
    }


    @Override
    public List<E> buscar(Specification specification) {
        return this.getJpaSpecificationExecutor().findAll(specification,getOrdenamiento());
    }

    @Override
    public List<E> buscar(Specification specification, Sort ordenamiento) {
        return getJpaSpecificationExecutor().findAll(specification,(ordenamiento == null ? getOrdenamiento():ordenamiento));
    }

    @Override
    public Optional<E> buscarUnico(Specification specification) {
        return this.getJpaSpecificationExecutor().findOne(specification);
    }
}
