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
@Table(name = "DETALLE_FACTURAS")
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 8789461951506937526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private String concepto;
    private Double subtotal;

    @JoinColumn(name = "ID_FACTURA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura factura;

    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Libro libro;


}
