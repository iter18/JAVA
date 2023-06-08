package com.iter.springboot.apirest.dtos;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoricoProductoDto implements Serializable {

    private Long id;
    private Integer cantidad;
    private Integer minimo;
    private Double precioCompra;
    private Double precioVenta;
    private LocalDateTime fecha;
    private String movimiento;
    private LibroDto libro;

}
