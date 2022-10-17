package com.curso.springbooterror.models.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Integer id;
    private String nombre;
    private String apellido;
}
