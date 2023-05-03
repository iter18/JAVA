package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario,Long>, JpaSpecificationExecutor<Inventario> {
}
