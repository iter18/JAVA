package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.HistoricoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoLibroRepository extends JpaRepository<HistoricoLibro,Long>, JpaSpecificationExecutor<HistoricoLibro> {
}
