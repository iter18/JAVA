package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorDto implements Serializable {
    private static final long serialVersionUID = 6959360790358112410L;

    private Long id;
    private String nombre;
    private String apellido;

}
