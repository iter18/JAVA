package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>, JpaSpecificationExecutor<Cliente> {
}
