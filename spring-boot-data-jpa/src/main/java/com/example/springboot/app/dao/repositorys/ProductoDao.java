package com.example.springboot.app.dao.repositorys;

import com.example.springboot.app.models.entity.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoDao extends JpaRepository<Producto,Long>, JpaSpecificationExecutor<Producto> {

    //alternartiva 1 para obtner la lista con like
    //método para obtener un listado de productos por filtros de búsqueda
    /*@Query("SELECT p FROM Producto p WHERE p.nombre like %?1%")
    public List<Producto> buscar(String term);

    //Alternativa 2 para obtener un list con metodos definido de JPA
    public List<Producto> findByNombreLikeIgnoreCase(String term);*/

}
