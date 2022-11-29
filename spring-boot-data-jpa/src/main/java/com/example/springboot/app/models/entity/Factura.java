package com.example.springboot.app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="facturas")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;
}
