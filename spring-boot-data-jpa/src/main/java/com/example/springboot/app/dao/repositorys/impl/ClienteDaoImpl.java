package com.example.springboot.app.dao.repositorys.impl;

import com.example.springboot.app.dao.repositorys.ClienteDao;
import com.example.springboot.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClienteDaoImpl implements ClienteDao {
    /* EntityManager sirve para realizar transacciones con los entitys declarados,
        en este caso estamos utilizando JPA Hibernate y se necesita declarar el entity con el que va interactuar
        el repository, en caso de DATA JPA y API CRUD Repository no es necesario declarar.

        Como no estamos especificando ningun motor por defecto SpringBoot utiliza la motor H2 que trae embebido,
        por lo tanto las transacciones las hacemos a nivel entity
     */

    @PersistenceContext
    private EntityManager em;
    /*
    * La anotación Transactional sirve para realizar efectivos los cambios en la bd, en este caso le estamos
    * diciendo en la propiedad que sea de sólo lectura, para que no se usen para modificar los datos.
    * */

    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }


    @Override
    public void save(Cliente cliente) {
        if(cliente.getId()!=null && cliente.getId()>0){
            //es igual que saveAndFlush
            em.merge(cliente);
        }else{
            //es igual que save para guardar
            em.persist(cliente);
        }

    }

    @Override
    public Cliente findById(Long id) {
        //es como findOne
        return em.find(Cliente.class,id);
    }

    @Override
    public void delete(Long id) {
        //Es igual que delete elimina
        em.remove(findById(id));
    }
}
