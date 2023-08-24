package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.dtos.ProductoInventarioDto;
import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.mappers.InventarioMapper;
import com.iter.springboot.apirest.modelo.*;
import com.iter.springboot.apirest.repository.InventarioRepository;
import com.iter.springboot.apirest.repository.specification.HistoricoLibroSpecification;
import com.iter.springboot.apirest.repository.specification.InventarioSpecification;
import com.iter.springboot.apirest.repository.specification.KardexSpecification;
import com.iter.springboot.apirest.service.*;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class InventarioServiceImpl extends AbstractQueryAvanzadoService<Inventario,Long> implements InventarioService {

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    LibroService libroService;

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    InventarioMapper inventarioMapper;

    @Autowired
    KardexService kardexService;

    @Autowired
    HistoricoLibroService historicoLibroService;



    @Override
    public JpaSpecificationExecutor<Inventario> getJpaSpecificationExecutor() {
        return inventarioRepository;
    }

    @Override
    public JpaRepository<Inventario, Long> getJpaRepository() {
        return inventarioRepository;
    }

    @Override
    public Sort getOrdenamiento() {
        return Sort.by(Inventario_.id.getName()).ascending();
    }

    @Override
    public List<InventarioDto> buscar(String isbn, String titulo) {

        Specification<Inventario> filtro = InventarioSpecification.tituloLibro(titulo)
                .and(InventarioSpecification.isbnLibro(isbn));

        return inventarioMapper.toListDto(this.buscar(filtro));
    }

    @Override
    @Transactional
    public InventarioDto altaProducto(ProductoInventarioDto productoInventarioDto) {
        Assert.notNull(productoInventarioDto.getMinimo(),"El minimo es campo requerido");
        Assert.notNull(productoInventarioDto.getStock(),"El stock es campo requerido");
        Assert.notNull(productoInventarioDto.getPrecioCompra(),"El precio de compra es campo requerido");
        Assert.notNull(productoInventarioDto.getPrecioVenta(),"El precio de venta es campo requerido");
        try {

            //verificación de existencia de libro y obtenemos al mismo tiempo
            Libro libro = libroService.buscarPorId(productoInventarioDto.getLibro().getId())
                    .orElseThrow(() -> new EntityNotFoundException("El libro no existe en la DB"));

            Movimiento movimiento = movimientoService.buscarPorId(productoInventarioDto.getIdMovimiento())
                    .orElseThrow(() -> new EntityNotFoundException("El movimiento no se encuntra registrado"));

            //Validar que no exista registro de libro/producto registrado
            Specification<Inventario> filtro = InventarioSpecification.idLibro(libro.getId());

            Optional<Inventario> registroExiste = this.buscarUnico(filtro);

            if(registroExiste.isPresent()){
                throw  new IllegalArgumentException("Ya se encuentra dado de alta este producto");
            }

            Inventario inv = Inventario.builder()
                    .libro(libro)
                    .stock(productoInventarioDto.getStock())
                    .minimo(productoInventarioDto.getMinimo())
                    .precioCompra(productoInventarioDto.getPrecioCompra())
                    .precioVenta(productoInventarioDto.getPrecioVenta())
                    .baja(0)
                    .build();
            Inventario inventario = inventarioRepository.save(inv);

            HistoricoLibro historicoLibro = HistoricoLibro.builder()
                    .cantidad(inventario.getStock())
                    .fecha(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                    .minimo(inventario.getMinimo())
                    .precioCompra(inventario.getPrecioCompra())
                    .precioVenta(inventario.getPrecioVenta())
                    .libro(libro)
                    .movimiento(movimiento)
                    .baja(0)
                    .build();
            historicoLibroService.alta(historicoLibro);

            Kardex kardex = Kardex.builder()
                    .precio(inventario.getPrecioVenta())
                    .cantidadInicial(inventario.getStock())
                    .entradas(0)
                    .salidas(0)
                    .cantidadFinal(inventario.getStock())
                    .fechaMovimiento(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                    .libro(libro)
                    .movimiento(movimiento)
                    .build();

            kardexService.alta(kardex);

            return inventarioMapper.toDto(inventario);
        }catch (DataAccessException e){
            log.error("error: {}", e.getStackTrace());
            throw  e;
        }

    }

    @Override
    @Transactional
    public InventarioDto modificarProducto(ProductoInventarioDto inventarioDto) {
        Assert.notNull(inventarioDto.getMinimo(),"El minimo es campo requerido");
        Assert.notNull(inventarioDto.getStock(),"El stock es campo requerido");
        Assert.notNull(inventarioDto.getPrecioCompra(),"El precio de compra es campo requerido");
        Assert.notNull(inventarioDto.getPrecioVenta(),"El precio de venta es campo requerido");
        try {
                Inventario inventario = this.buscarPorId(inventarioDto.getIdInventario())
                        .orElseThrow(()-> new EntityNotFoundException("No se encontro el registro en el inventario"));
                Movimiento movimiento = movimientoService.buscarPorId(inventarioDto.getIdMovimiento())
                        .orElseThrow(() -> new EntityNotFoundException("El tipo de movimiento no existe!"));
                Libro libro  = libroService.buscarPorId(inventarioDto.getLibro().getId())
                        .orElseThrow(()-> new EntityNotFoundException("No se encontro el libro seleccionado"));

                Specification<Kardex> filtro = KardexSpecification.idLibro(libro.getId());

                List<Kardex> registrosProductoKardex = kardexService.buscar(filtro,Sort.by(Kardex_.fechaMovimiento.getName()).descending());
                boolean bandera = true;

                //Validación para saber si hay más de un moivimiento en Kardex del producto
               //Código de con if anidados y mala practica
                /*if(registrosProductoKardex.size()>1){
                    if(registrosProductoKardex.get(0).getMovimiento().getId() != 7){
                        if(!inventarioDto.getStock().equals(registrosProductoKardex.get(0).getCantidadFinal())){
                            throw new IllegalArgumentException("Operación no permitida: el producto ya tiene movimientos, ingrese una devolución");
                        }else{
                            bandera = false;
                        }
                    }else{
                        bandera = true;
                    }

                }else{
                    bandera = true;
                }
                if(bandera){
                    inventario.setStock(inventarioDto.getStock());
                    inventario.setMinimo(inventarioDto.getMinimo());
                    inventario.setPrecioVenta(inventarioDto.getPrecioVenta());
                    inventario.setPrecioCompra(inventarioDto.getPrecioCompra());
                    registrosProductoKardex.get(0).setCantidadInicial(inventarioDto.getStock());
                    registrosProductoKardex.get(0).setCantidadFinal(inventarioDto.getStock());
                    registrosProductoKardex.get(0).setPrecio(inventarioDto.getPrecioVenta());
                }
                if(!bandera){
                    inventario.setMinimo(inventarioDto.getMinimo());
                    inventario.setPrecioVenta(inventarioDto.getPrecioVenta());
                    inventario.setPrecioCompra(inventarioDto.getPrecioCompra());
                }*/

                //Codigo optmizado con clausulas de guarda
                if(registrosProductoKardex.size() >1){
                    if(registrosProductoKardex.get(0).getMovimiento().getId() != 7){
                        if (!inventarioDto.getStock().equals(registrosProductoKardex.get(0).getCantidadFinal())) {
                            throw new IllegalArgumentException("Operación no permitida: el producto ya tiene movimientos, ingrese una devolución");
                        }
                        bandera = false;
                    }
                }
                if(bandera){
                    inventario.setStock(inventarioDto.getStock());
                    registrosProductoKardex.get(0).setCantidadInicial(inventarioDto.getStock());
                    registrosProductoKardex.get(0).setCantidadFinal(inventarioDto.getStock());
                    registrosProductoKardex.get(0).setPrecio(inventarioDto.getPrecioVenta());
                }
                inventario.setMinimo(inventarioDto.getMinimo());
                inventario.setPrecioVenta(inventarioDto.getPrecioVenta());
                inventario.setPrecioCompra(inventarioDto.getPrecioCompra());

                Inventario inventarioM = inventarioRepository.saveAndFlush(inventario);
                HistoricoLibro historicoLibro = HistoricoLibro.builder()
                        .cantidad(inventarioDto.getStock())
                        .fecha(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                        .minimo(inventario.getMinimo())
                        .precioVenta(inventario.getPrecioVenta())
                        .precioCompra(inventarioDto.getPrecioCompra())
                        .libro(libro)
                        .baja(0)
                        .movimiento(movimiento)
                        .build();


                historicoLibroService.alta(historicoLibro);
                kardexService.modificar(registrosProductoKardex.get(0));

                log.info("obj: {}",inventario,registrosProductoKardex.toArray());
                return inventarioMapper.toDto(inventarioM);

        }catch (DataAccessException e){
            log.error("error{}",e.getStackTrace());
            throw e;
        }

    }

    @Override
    @Transactional
    public InventarioDto reordenProducto(String userName, ProductoInventarioDto productoInventarioDto) {
        Assert.notNull(productoInventarioDto.getPrecioCompra(),"El precio de compra es campo requerido");
        Assert.notNull(productoInventarioDto.getCantidadReorden(),"El campo cantidad reorden es requerido");
        try{
            Inventario inventario = this.buscarPorId(productoInventarioDto.getIdInventario())
                    .orElseThrow(()-> new EntityNotFoundException("No se encontro el registro en el inventario"));
            Movimiento movimiento = movimientoService.buscarPorId(productoInventarioDto.getIdMovimiento())
                    .orElseThrow(() -> new EntityNotFoundException("El tipo de movimiento no existe!"));
            Libro libro  = libroService.buscarPorId(productoInventarioDto.getLibro().getId())
                    .orElseThrow(()-> new EntityNotFoundException("No se encontro el libro seleccionado"));

            Specification<Kardex> filtro = KardexSpecification.idLibro(libro.getId());
            List<Kardex> registrosProductoKardex = kardexService.buscar(filtro,Sort.by(Kardex_.fechaMovimiento.getName()).descending());

            int cantidadInicial  = registrosProductoKardex.get(0).getCantidadInicial();
            int entradas = registrosProductoKardex.get(0).getEntradas();
            int salidas = registrosProductoKardex.get(0).getSalidas();


            int cantidadFinal = (cantidadInicial+(entradas+ productoInventarioDto.getCantidadReorden()))-salidas;
            int stockInventario = inventario.getStock();
            int stockFinalInventario =  stockInventario + productoInventarioDto.getCantidadReorden();

            inventario.setStock(stockFinalInventario);
            inventario.setPrecioCompra(productoInventarioDto.getPrecioCompra());

            Kardex kardex = Kardex.builder()
                    .precio(inventario.getPrecioVenta())
                    .cantidadInicial(registrosProductoKardex.get(0).getCantidadFinal())
                    .entradas(productoInventarioDto.getCantidadReorden())
                    .salidas(0)
                    .cantidadFinal(cantidadFinal)
                    .fechaMovimiento(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                    .libro(libro)
                    .movimiento(movimiento)
                    .build();

            HistoricoLibro historicoLibro = HistoricoLibro.builder()
                    .cantidad(productoInventarioDto.getCantidadReorden())
                    .minimo(inventario.getMinimo())
                    .precioCompra(inventario.getPrecioCompra())
                    .precioVenta(inventario.getPrecioVenta())
                    .fecha(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                    .libro(libro)
                    .movimiento(movimiento)
                    .baja(0)
                    .build();


            Inventario inventarioM = inventarioRepository.saveAndFlush(inventario);
            kardexService.alta(kardex);
            historicoLibroService.alta(historicoLibro);
            log.info("Usuario:"+userName +" realizó: "+ inventario,kardex,historicoLibro );
            return inventarioMapper.toDto(inventarioM);
        }catch (DataAccessException e){
            throw e;
        }
    }

    @Override
    @Transactional
    public InventarioDto bajaProducto(Long idInventario) {

        Inventario inventario = this.buscarPorId(idInventario)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el registro en inventario"));

        Movimiento movimiento = movimientoService.buscarPorId(Long.parseLong("6"))
                .orElseThrow(() -> new EntityNotFoundException("El movimiento no fue encontrado"));


        Specification<HistoricoLibro> filtro = HistoricoLibroSpecification.idLibro(inventario.getLibro().getId());

        List<HistoricoLibro> historicoLibroList = historicoLibroService.buscar(filtro)
                .stream()
                .map(historicoLibro -> {
                    historicoLibro.setBaja(1);
                    return historicoLibro;
                })
                .collect(Collectors.toList());

        if(historicoLibroList.isEmpty()){
            throw new IllegalArgumentException("No existen registros historicos del producto");
        }

        HistoricoLibro historicoLibroAdd = HistoricoLibro.builder()
                .cantidad(inventario.getStock())
                .fecha(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                .libro(inventario.getLibro())
                .movimiento(movimiento)
                .minimo(inventario.getMinimo())
                .precioVenta(inventario.getPrecioVenta())
                .precioCompra(inventario.getPrecioCompra())
                .baja(1)
                .build();

        Kardex kardex = Kardex.builder()
                .precio(0.0)
                .cantidadInicial(inventario.getStock())
                .entradas(0)
                .salidas(inventario.getStock())
                .cantidadFinal(0)
                .fechaMovimiento(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                .libro(inventario.getLibro())
                .movimiento(movimiento)
                .build();

        inventario.setStock(0);
        inventario.setMinimo(0);
        inventario.setPrecioCompra(0.0);
        inventario.setPrecioVenta(0.0);
        inventario.setBaja(1);

        kardexService.alta(kardex);
        historicoLibroService.modificar(historicoLibroList);
        historicoLibroService.alta(historicoLibroAdd);
        return inventarioMapper.toDto(inventarioRepository.saveAndFlush(inventario));


    }

    @Override
    @Transactional
    public InventarioDto reingresoProducto(ProductoInventarioDto inventarioDto) {
        Assert.notNull(inventarioDto.getMinimo(),"El minimo es campo requerido");
        Assert.notNull(inventarioDto.getStock(),"El stock es campo requerido");
        Assert.notNull(inventarioDto.getPrecioCompra(),"El precio de compra es campo requerido");
        Assert.notNull(inventarioDto.getPrecioVenta(),"El precio de venta es campo requerido");

        try{
            Inventario inventario = this.buscarPorId(inventarioDto.getIdInventario())
                    .orElseThrow(()-> new EntityNotFoundException("No se encontro el registro en el inventario"));
            Movimiento movimiento = movimientoService.buscarPorId(inventarioDto.getIdMovimiento())
                    .orElseThrow(() -> new EntityNotFoundException("El tipo de movimiento no existe!"));
            Libro libro  = libroService.buscarPorId(inventarioDto.getLibro().getId())
                    .orElseThrow(()-> new EntityNotFoundException("No se encontro el libro seleccionado"));

            inventario.setStock(inventarioDto.getStock());
            inventario.setMinimo(inventarioDto.getMinimo());
            inventario.setPrecioVenta(inventarioDto.getPrecioVenta());
            inventario.setPrecioCompra(inventarioDto.getPrecioCompra());
            inventario.setBaja(0);

            HistoricoLibro historicoLibro = HistoricoLibro.builder()
                    .cantidad(inventario.getStock())
                    .fecha(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                    .minimo(inventario.getMinimo())
                    .precioCompra(inventario.getPrecioCompra())
                    .precioVenta(inventario.getPrecioVenta())
                    .libro(libro)
                    .movimiento(movimiento)
                    .baja(0)
                    .build();
            historicoLibroService.alta(historicoLibro);

            Kardex kardex = Kardex.builder()
                    .precio(inventario.getPrecioVenta())
                    .cantidadInicial(inventario.getStock())
                    .entradas(0)
                    .salidas(0)
                    .cantidadFinal(inventario.getStock())
                    .fechaMovimiento(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                    .libro(libro)
                    .movimiento(movimiento)
                    .build();
            kardexService.alta(kardex);

            return inventarioMapper.toDto(inventarioRepository.saveAndFlush(inventario));

        }catch (DataAccessException e){
            log.error("error{}",e.getStackTrace());
            throw e;
        }
    }


}
