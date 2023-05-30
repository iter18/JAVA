package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.InventarioDto;
import com.iter.springboot.apirest.dtos.ProductoInventarioDto;
import com.iter.springboot.apirest.genericos.negocio.impl.AbstractQueryAvanzadoService;
import com.iter.springboot.apirest.mappers.InventarioMapper;
import com.iter.springboot.apirest.modelo.Inventario;
import com.iter.springboot.apirest.modelo.Kardex;
import com.iter.springboot.apirest.modelo.Libro;
import com.iter.springboot.apirest.modelo.Movimiento;
import com.iter.springboot.apirest.repository.InventarioRepository;
import com.iter.springboot.apirest.repository.specification.InventarioSpecification;
import com.iter.springboot.apirest.service.InventarioService;
import com.iter.springboot.apirest.service.KardexService;
import com.iter.springboot.apirest.service.LibroService;
import com.iter.springboot.apirest.service.MovimientoService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Override
    public JpaSpecificationExecutor<Inventario> getJpaSpecificationExecutor() {
        return inventarioRepository;
    }

    @Override
    public JpaRepository<Inventario, Long> getJpaRepository() {
        return inventarioRepository;
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
        Assert.notNull(productoInventarioDto.getPrecio(),"El precio es campo requerido");


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
                .precio(productoInventarioDto.getPrecio())
                .build();
        Inventario inventario = inventarioRepository.save(inv);

        Kardex kardex = Kardex.builder()
                .precio(inventario.getPrecio())
                .cantidadInicial(inventario.getStock())
                .fechaMovimiento(LocalDateTime.now(ZoneId.of("America/Mexico_City")))
                .libro(libro)
                .movimiento(movimiento)
                .build();

        kardexService.alta(kardex);

        return inventarioMapper.toDto(inventario);
    }

    @Override
    public InventarioDto modificarProducto(ProductoInventarioDto inventarioDto) {
        Inventario inventario = this.buscarPorId(inventarioDto.getIdInventario())
                        .orElseThrow(()-> new EntityNotFoundException("No se encontro el registro en el inventario"));
        Movimiento movimiento = movimientoService.buscarPorId(inventarioDto.getIdMovimiento())
                        .orElseThrow(() -> new EntityNotFoundException("El tipo de movimiento no existe!"));

        log.info("obj: {}",inventarioDto.getLibro().getId());
        return null;
    }


}
