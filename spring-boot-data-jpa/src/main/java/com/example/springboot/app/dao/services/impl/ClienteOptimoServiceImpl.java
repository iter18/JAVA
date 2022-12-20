package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.repositorys.ClienteDao;
import com.example.springboot.app.dao.repositorys.FacturaDao;
import com.example.springboot.app.dao.repositorys.ProductoDao;
import com.example.springboot.app.dao.repositorys.spec.ProductoSpecification;
import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.dao.services.UploadFileService;
import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class ClienteOptimoServiceImpl implements ClienteOptimoService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private FacturaDao facturaDao;

    @Autowired
    private UploadFileService uploadFileService;


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
    public void guardar(Cliente cliente, MultipartFile file) {
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

    @Transactional
    @Override
    public void eliminarFactura(Long id) {
        facturaDao.deleteById(id);
    }
}
