package com.example.springboot.app.dao.services;

import com.example.springboot.app.models.entity.Cliente;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ClienteOptimoService {
    List<Cliente> findAll();

    //método para listar todos los registros pero con paginación
    Page<Cliente> findAll(Pageable pageable);

    void guardar(Cliente cliente, MultipartFile file);

    Cliente findById(Long id);

    void delete(Long id);

    public List<Producto> buscar(String term);

    public void saveFactura(Factura factura);

    public Producto buscarProductoBy(Long id);

    Factura buscar(Long id);

    void eliminarFactura(Long id);

}
