package com.iter.springboot.apirest.service.impl;

import com.iter.springboot.apirest.dtos.AltaProductoInventarioDto;
import com.iter.springboot.apirest.dtos.InventarioDto;
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
import java.util.Date;
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
    @Transactional
    public InventarioDto altaProducto(AltaProductoInventarioDto altaProductoInventarioDto) {

        Assert.notNull(altaProductoInventarioDto.getMinimo(),"El minimo es campo requerido");
        Assert.notNull(altaProductoInventarioDto.getStock(),"El stock es campo requerido");
        Assert.notNull(altaProductoInventarioDto.getPrecio(),"El precio es campo requerido");

        //verificación de existencia de libro y obtenemos al mismo tiempo
        Libro libro = libroService.buscarPorId(altaProductoInventarioDto.getIdLibro())
                .orElseThrow(() -> new EntityNotFoundException("El libro no existe en la DB"));

        Movimiento movimiento = movimientoService.buscarPorId(altaProductoInventarioDto.getIdMovimiento())
                .orElseThrow(() -> new EntityNotFoundException("El movimiento no se encuntra registrado"));

        //Validar que no exista registro de libro/producto registrado
        Specification<Inventario> filtro = InventarioSpecification.idLibro(libro.getId());

        Optional<Inventario> registroExiste = this.buscarUnico(filtro);

        if(registroExiste.isPresent()){
            throw  new IllegalArgumentException("Ya se encuentra dado de alta este producto");
        }

        Inventario inv = Inventario.builder()
                .libro(libro)
                .stock(altaProductoInventarioDto.getStock())
                .minimo(altaProductoInventarioDto.getMinimo())
                .precio(altaProductoInventarioDto.getPrecio())
                .build();
        Inventario inventario = inventarioRepository.save(inv);

        Kardex kardex = Kardex.builder()
                .precio(inventario.getPrecio())
                .cantidad(inventario.getStock())
                .fechaMovimiento(new Date())
                .libro(libro)
                .movimiento(movimiento)
                .build();

        kardexService.alta(kardex);

        return inventarioMapper.toDto(inventario);
    }


}
