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
@Table(name = "FACTURAS")
public class Factura implements Serializable {

    private static final long serialVersionUID = -650896675036547192L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer folio;
    private String descripcion;
    private Double total;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura")
    private List<DetalleFactura> detalleFacturaList;

}
