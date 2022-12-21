package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.repositorys.ClienteOptimoDao;
import com.example.springboot.app.dao.repositorys.InvoiceDao;
import com.example.springboot.app.dao.repositorys.ProductoDao;
import com.example.springboot.app.dao.repositorys.spec.ProductoSpecification;
import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.dao.services.UploadFileService;
import com.example.springboot.app.models.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClienteOptimoServiceImpl implements ClienteOptimoService {

    @Autowired
    private ClienteOptimoDao clienteDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private InvoiceDao facturaDao;

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
                Specification<Producto> filtro = ProductoSpecification.nombreLike(term);
                List<Producto> productoList = productoDao.findAll(filtro);
                return productoList;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional
    public void saveFactura(Invoice factura,List<Long> productoId,List<Integer>cantidad) {
        try {

                AtomicInteger i = new AtomicInteger();
                List<ItemInvoice> itemInvoiceList =  productoId.stream().map(productos -> {
                    Double importe = 0.0;

                    Producto producto = this.buscarProductoBy(productos);
                    ItemInvoice itemInvoice = new ItemInvoice();
                    itemInvoice.setCantidad(cantidad.get(i.get()));
                    itemInvoice.setProducto(producto);
                    importe = cantidad.get(i.get())*producto.getPrecio();
                    itemInvoice.setImporte(importe);
                    i.set(i.get() + 1);
                    return  itemInvoice;
                }).collect(Collectors.toList());

                factura.setItemsFactura(itemInvoiceList);
                Double total = itemInvoiceList.stream().mapToDouble(item-> item.getImporte()).sum();
                factura.setTotal(total);
                factura.setCreateAt(new Date());
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
    public Invoice buscar(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void eliminarFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    public Invoice crearFactura(Long id) {
        try {
            Client cliente = this.findById(id);
            if(cliente == null){
                return null;
            }
            Invoice factura = new Invoice();
            factura.setCliente(cliente);
            return factura;
        }catch (Exception e){
            throw e;
        }
    }
}
