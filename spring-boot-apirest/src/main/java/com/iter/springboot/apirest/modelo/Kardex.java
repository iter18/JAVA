package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "kardex")
public class Kardex implements Serializable {

    private static final long serialVersionUID = -6640610040606128045L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precio;

    @Column(name = "CANTIDAD_INICIAL")
    private Integer cantidadInicial;

    private Integer entradas;
    private Integer salidas;

    @Column(name = "CANTIDAD_FINAL")
    private Integer cantidadFinal;

    @Column(name = "FECHA_MOVIMIENTO")
    private LocalDateTime fechaMovimiento;

    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Libro libro;

    @JoinColumn(name = "ID_MOVIMIENTO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;


}
