package com.iter.springboot.apirest.genericos.negocio;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface QueryAvanzadoService<E,K> extends QueryService<E,K>{

    JpaSpecificationExecutor<E> getJpaSpecificationExecutor();

    List<E> buscar(Specification var1);

    Optional<E> buscarUnico(Specification var1);
}
