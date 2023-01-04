package com.example.springboot.app.dao.services.impl;

import com.example.springboot.app.dao.repositorys.ClienteOptimoDao;
import com.example.springboot.app.dao.repositorys.InvoiceDao;
import com.example.springboot.app.dao.repositorys.ProductoDao;
import com.example.springboot.app.dao.repositorys.spec.ProductoSpecification;
import com.example.springboot.app.dao.services.ClienteOptimoService;
import com.example.springboot.app.dao.services.UploadFileService;
import com.example.springboot.app.models.entity.*;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
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
    public Invoice eliminarFactura(Long id) {
        try {
            Invoice factura = this.buscar(id);
            if(factura!=null){
                facturaDao.deleteById(id);
            }
            return factura;
        }catch (Exception e){
            throw e;
        }
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

    @Override
    public String exportPDF(String format,Long id) throws JRException, FileNotFoundException, UnsupportedEncodingException {
        try {
                String destino = "C:\\reportes";

                Invoice factura = this.buscar(id);
                if(factura == null){
                    return null;
                }

                List<Producto> productoList = factura.getItemsFactura().stream().map(itemInvoice -> itemInvoice.getProducto()).collect(Collectors.toList());

                Map<String,Object> parameters = new HashMap<>();
                parameters.put("ds",factura.getItemsFactura());
                parameters.put("folio",factura.getId().toString());
                JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(factura.getItemsFactura());

                File file = ResourceUtils.getFile("classpath:reportes/ReporteFactura.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(URLDecoder.decode(file.getAbsolutePath(),"UTF-8"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,jrBeanCollectionDataSource);

                JasperExportManager.exportReportToPdfFile(jasperPrint,destino+"\\factura.pdf");

            return  "El reporte se ha generado";
        }catch (Exception e){
            throw e;
        }

    }
}
