package com.iter.springboot.apirest.modelo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "PRESTAMOS_CLIENTES")
public class PrestamoClienteFactura implements Serializable {

    private static final long serialVersionUID = -3755408121848124595L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "ID_FACTURA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura factura;

    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;


    @JoinColumn(name = "ID_PRESTAMO", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Prestamo prestamo;

    @OneToMany(mappedBy = "prestamoClienteFactura",fetch = FetchType.LAZY)
    private List<PrestamoCliente> prestamoClienteList;

}
