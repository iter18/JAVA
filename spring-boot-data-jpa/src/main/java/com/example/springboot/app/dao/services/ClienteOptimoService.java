package com.example.springboot.app.dao.services;

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.models.entity.Factura;
import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.models.entity.Producto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ClienteOptimoService {
    List<Client> findAll();

    //método para listar todos los registros pero con paginación
    Page<Client> findAll(Pageable pageable);

    void guardar(Client cliente, MultipartFile file);

    Client findById(Long id);

    Boolean delete(Long id);

    public List<Producto> buscar(String term);

    public void saveFactura(Invoice factura, List<Long> productoId, List<Integer> cantidad);

    public Producto buscarProductoBy(Long id);

    Invoice buscar(Long id);

    Invoice eliminarFactura(Long id);

    Invoice crearFactura(Long id);

    String exportPDF(String format,Long id) throws JRException, FileNotFoundException, UnsupportedEncodingException;

}
