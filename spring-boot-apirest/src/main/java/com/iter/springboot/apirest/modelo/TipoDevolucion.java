package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TIPO_DEVOLUCION")
public class TipoDevolucion implements Serializable {

    private static final long serialVersionUID = 4885351309828024609L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
}
