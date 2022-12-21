package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.repositorys.ClienteOptimoDao;
import com.example.springboot.app.dao.repositorys.FacturaDao;
import com.example.springboot.app.dao.repositorys.ProductoDao;
import com.example.springboot.app.dao.repositorys.spec.ProductoSpecification;
import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.dao.services.UploadFileService;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
public class ClienteOptimoServiceImpl implements ClienteOptimoService {

    @Autowired
    private ClienteOptimoDao clienteDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private FacturaDao facturaDao;

    @Autowired
    private UploadFileService uploadFileService;


    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) clienteDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void guardar(Client cliente, MultipartFile file) {
        try {
            //Proceso para reemplazar foto cuando se edita
            if(cliente.getId() != null && cliente.getId()>0 && cliente.getFoto()!=null && cliente.getFoto().length() > 0){
                uploadFileService.delete(cliente.getFoto());
            }
            String fileName = uploadFileService.copy(file);
            //setteo de nombre la foto para que se alamacene en el DB
                cliente.setFoto(fileName);
                clienteDao.save(cliente);

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        Boolean respuesta = false;
        try {
            if(id>0 || id != null){
                Client cliente = this.findById(id);
                boolean resp = uploadFileService.delete(cliente.getFoto());
                if (resp){
                    clienteDao.deleteById(id);
                    respuesta =  true;
                }else{
                    respuesta = false;
                }
            }else{
                respuesta = false;
            }
        }catch (Exception e){
            throw e;
        }

        return respuesta;
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

    @Transactional
    @Override
    public void eliminarFactura(Long id) {
        facturaDao.deleteById(id);
    }
}
