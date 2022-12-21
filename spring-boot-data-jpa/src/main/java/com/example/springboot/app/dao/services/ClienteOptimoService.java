package com.example.springboot.app.dao.services;

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClienteOptimoService {
    List<Client> findAll();

    //método para listar todos los registros pero con paginación
    Page<Client> findAll(Pageable pageable);

    void guardar(Client cliente, MultipartFile file);

    Client findById(Long id);

    Boolean delete(Long id);

    public List<Producto> buscar(String term);

    public void saveFactura(Factura factura);

    public Producto buscarProductoBy(Long id);

    Factura buscar(Long id);

    void eliminarFactura(Long id);

}
