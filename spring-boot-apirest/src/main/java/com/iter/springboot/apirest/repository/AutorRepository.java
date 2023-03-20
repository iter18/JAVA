package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long>, JpaSpecificationExecutor<Autor> {
}
