package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Cliente;

import java.util.List;

public interface ClienteDao {
    
    List<Cliente> findAll();
}