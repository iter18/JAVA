package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//Esto es la implementación sin usar Paginacion, es lo tradicional
//public interface ClienteDao extends CrudRepository<Cliente,Long> {

//esto es la menera hacer lo mismo pero ahora con paginación
public interface ClienteDao extends PagingAndSortingRepository<Cliente,Long> {

    /*
    * Este proceso es como hacerlo de manera manual sin necesidad de hacer uso de CrudRepository
    * Para implementar el CrudRepository es necesario heredar
    List<Cliente> findAll();

    void save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);*/
}
