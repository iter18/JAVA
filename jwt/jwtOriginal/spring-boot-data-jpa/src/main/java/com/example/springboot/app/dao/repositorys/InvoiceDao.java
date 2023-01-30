package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDao  extends JpaRepository<Invoice,Long>, JpaSpecificationExecutor<Invoice> {
}
