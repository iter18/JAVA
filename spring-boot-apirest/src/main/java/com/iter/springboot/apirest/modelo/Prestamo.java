package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "PRESTAMOS")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = -5325610845154958632L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periodo;

    @Column(name = "PRECIO_PERIODO")
    private Double precioPeriodo;
}
