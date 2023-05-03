package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento,Long>, JpaSpecificationExecutor<Movimiento> {
}
