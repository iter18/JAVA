package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.repositorys.ClienteDao;
import com.example.springboot.app.dao.repositorys.FacturaDao;
import com.example.springboot.app.dao.repositorys.ProductoDao;
import com.example.springboot.app.dao.repositorys.spec.ProductoSpecification;
import com.example.springboot.app.dao.services.ClienteService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServicesImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private FacturaDao facturaDao;


    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    public List<Producto> buscar(String term) {
        try {
                //Método para buscar por query escribir consulta en repository
                //List<Producto> productoList = productoDao.buscar(term);
                //Método para buscar por método jpa en repository sin consulta
                //List<Producto> productoList = productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
                //Método para hacerlo por Specification
                Specification<Producto> filtro = ProductoSpecification.nombreLike(term);
                List<Producto> productoList = productoDao.findAll(filtro);
            return productoList;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        try {
                facturaDao.save(factura);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public Producto buscarProductoBy(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    public Factura buscar(Long id) {
        return facturaDao.findById(id).orElse(null);
    }
}
