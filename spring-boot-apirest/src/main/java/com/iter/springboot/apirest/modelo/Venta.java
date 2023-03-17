package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "VENTAS")
public class Venta implements Serializable {

    private static final long serialVersionUID = -1072884865527807548L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;

    @Column(name = "FECHA_VENTA")
    private Date fechaVenta;

    @JoinColumn(name = "ID_FACTURA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura factura;

    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;


    @OneToMany(mappedBy = "venta",fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentaList;
}
