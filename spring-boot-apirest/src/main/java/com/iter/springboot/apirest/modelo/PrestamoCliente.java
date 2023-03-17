package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "PRESTAMOS_CLIENTES")
public class PrestamoCliente implements Serializable {

    private static final long serialVersionUID = 9157615753029073008L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Integer estatus;
    private String observacion;

    @Column(name = "FECHA_PRESTAMO")
    private Date fechaPrestamo;

    @Column(name = "FECHA_ENTREGA")
    private Date fechaEntrega;

    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Libro libro;

    @JoinColumn(name = "ID_PRESTAMO_CLIENTE_FACTURA")
    @ManyToOne(fetch = FetchType.LAZY)
    private PrestamoClienteFactura prestamoClienteFactura;

}
