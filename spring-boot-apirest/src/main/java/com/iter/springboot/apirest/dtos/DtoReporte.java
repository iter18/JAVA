package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DtoReporte implements Serializable {

    private static final long serialVersionUID = 8390424607963311619L;
    private Long id;
    private Integer cantidad;
    private Integer minimo;
    private Double precioCompra;
    private Double precioVenta;
    private String movimiento;
    private LibroDto libroDto;
}
