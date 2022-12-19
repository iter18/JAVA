package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FacturaDao extends JpaRepository<Factura,Long>, JpaSpecificationExecutor<Factura> {
}
