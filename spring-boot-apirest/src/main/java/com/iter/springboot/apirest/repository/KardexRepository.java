package com.iter.springboot.apirest.repository;

import com.iter.springboot.apirest.modelo.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KardexRepository extends JpaRepository<Kardex,Long>, JpaSpecificationExecutor<Kardex> {
}
