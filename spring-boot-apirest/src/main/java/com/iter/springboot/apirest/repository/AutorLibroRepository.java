package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.AutorLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorLibroRepository extends JpaRepository<AutorLibro,Long>, JpaSpecificationExecutor<AutorLibro> {
}
