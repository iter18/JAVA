package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDao extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
}
