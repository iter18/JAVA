package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteDao extends CrudRepository<Cliente,Long> {
    /*
    * Este proceso es como hacerlo de manera manual sin necesidad de hacer uso de CrudRepository
    * Para implementar el CrudRepository es necesario heredar
    List<Cliente> findAll();

    void save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);*/
}
