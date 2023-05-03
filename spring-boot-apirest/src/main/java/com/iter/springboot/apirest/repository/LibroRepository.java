package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>, JpaSpecificationExecutor<Libro> {
    @Query("select l.id as code, l.titulo as valor from Libro l order by l.titulo asc")
    List<Tuple>buscarC();
}
