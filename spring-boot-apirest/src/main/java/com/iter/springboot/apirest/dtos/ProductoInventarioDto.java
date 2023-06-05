package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoInventarioDto implements Serializable {

    private static final long serialVersionUID = -1836596040881285053L;

    private Long idInventario;
    private Long idMovimiento;
    private Integer stock;
    private Integer minimo;
    private Double precioCompra;
    private Double precioVenta;
    private LibroDto libro;
    private Integer cantidadReorden;
}
