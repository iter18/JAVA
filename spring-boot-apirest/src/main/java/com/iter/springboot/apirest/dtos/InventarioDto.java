package com.iter.springboot.apirest.dtos;


import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventarioDto implements Serializable {

    private static final long serialVersionUID = 7365387308487701191L;

    private Long id;
    private Integer stock;
    private Integer minimo;
    private Double precio;
    private LibroDto libro;


}
