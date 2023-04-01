package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>, JpaSpecificationExecutor<Libro> {
}
