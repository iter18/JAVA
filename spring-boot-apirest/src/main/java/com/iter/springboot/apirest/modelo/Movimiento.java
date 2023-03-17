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
@Table(name = "MOVIMIENTOS")
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 7888113732037380089L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIPO_MOVIMIENTO")
    private String tipoMovimiento;

}
