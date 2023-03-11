package com.iter.springboot.apirest.dtos;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDto implements Serializable {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Date fechaCreacion;
}
