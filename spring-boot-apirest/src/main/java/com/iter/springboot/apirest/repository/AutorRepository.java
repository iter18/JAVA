package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long>, JpaSpecificationExecutor<Autor> {
    @Query("select a.id as code, concat(a.nombre,' ',a.apellido) as valor from Autor a order by a.nombre asc")
    List<Tuple> buscarC();
}
