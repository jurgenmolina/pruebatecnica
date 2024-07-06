package com.co.cetus.pruebatecnica.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recepcion_producto")
public class RecepcionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recepcion")
    private Integer id;

    @Column(name = "fecha_hora_recepcion", nullable = false)
    private LocalDateTime fechaHoraRecepcion;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "lote", nullable = false)
    private String lote;

    @Column(name = "registro_invima", nullable = false)
    private String registroInvima;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDateTime fechaVencimiento;

    @Column(name = "estado_presentacion")
    private String estadoPresentacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}