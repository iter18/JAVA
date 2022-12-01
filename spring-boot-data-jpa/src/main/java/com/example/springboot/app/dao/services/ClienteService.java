package com.example.springboot.app.dao.services;

import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();

    //método para listar todos los registros pero con paginación
    Page<Cliente> findAll(Pageable pageable);

    void save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);

    public List<Producto> buscar(String term);

}
