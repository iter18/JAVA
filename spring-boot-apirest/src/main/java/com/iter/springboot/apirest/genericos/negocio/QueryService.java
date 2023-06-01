package com.iter.springboot.apirest.genericos.negocio;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QueryService<E,K> {

    JpaRepository<E,K> getJpaRepository();

    Sort getOrdenamiento();

    List<E> buscar();

    Optional<E> buscarPorId(K variableId);
}
