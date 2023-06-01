package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.modelo.Kardex;
import com.iter.springboot.apirest.modelo.Kardex_;
import com.iter.springboot.apirest.repository.KardexRepository;
import com.iter.springboot.apirest.service.KardexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class KardexServiceImpl extends AbstractQueryAvanzadoService<Kardex,Long> implements KardexService {

   @Autowired
    KardexRepository kardexRepository;

    @Override
    public JpaSpecificationExecutor<Kardex> getJpaSpecificationExecutor() {
        return kardexRepository;
    }

    @Override
    public JpaRepository<Kardex, Long> getJpaRepository() {
        return kardexRepository;
    }

    @Override
    public Sort getOrdenamiento() {
        return Sort.by(Kardex_.fechaMovimiento.getName()).descending();
    }

    @Override
    @Transactional
    public Kardex alta(Kardex kardex) {
        return kardexRepository.save(kardex);
    }
}
