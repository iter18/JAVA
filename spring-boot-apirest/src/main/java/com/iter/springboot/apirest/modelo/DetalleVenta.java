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
@Table(name = "DETALLE_VENTAS")
public class DetalleVenta  implements Serializable {

    private static final long serialVersionUID = -3195032713259669177L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;
    private Double subtotal;


    @JoinColumn(name = "ID_LIBRO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Libro libro;

    @JoinColumn(name = "ID_VENTA")
    @ManyToOne(fetch = FetchType.LAZY)
    private Venta venta;


}
