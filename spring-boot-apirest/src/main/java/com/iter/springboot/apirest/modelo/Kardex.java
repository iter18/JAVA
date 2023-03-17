package com.iter.springboot.apirest.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class Kardex implements Serializable {

    private static final long serialVersionUID = -6640610040606128045L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precio;
    private Integer cantidad;

    @Column(name = "FECHA_MOVIMIENTO")
    private Date fechaMovimiento;

    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Libro libro;

    @JoinColumn(name = "ID_MOVIMIENTO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;


}
