package com.iter.springboot.apirest.genericos.negocio.impl;

import com.iter.springboot.apirest.genericos.negocio.QueryService;

import java.util.List;
import java.util.Optional;

public abstract class AbstractQueryService<E,K> implements QueryService<E,K> {
    public AbstractQueryService() {
    }

    public List<E> buscar() {
        return this.getJpaRepository().findAll();
    }


    public Optional<E> buscarPorId(K variableId) {
        return this.getJpaRepository().findById(variableId);
    }
}
