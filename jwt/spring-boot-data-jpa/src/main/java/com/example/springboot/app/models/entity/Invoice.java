package com.example.springboot.app.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="facturas")
@AllArgsConstructor
@NoArgsConstructor
public class Invoice implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String descripcion;
    private String observacion;
    private Double total;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @XmlTransient
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Client cliente;

    @JoinColumn(name = "factura_id")
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemInvoice> itemsFactura;


}
