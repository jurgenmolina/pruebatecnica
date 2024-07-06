package com.co.cetus.pruebatecnica.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer id;

    @Column(name = "tipo_identificacion", nullable = false)
    private String tipoIdentificacion;

    @Column(name = "identificacion", nullable = false, unique = true)
    private String identificacion;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Column(name = "celular_contacto")
    private String celularContacto;
}