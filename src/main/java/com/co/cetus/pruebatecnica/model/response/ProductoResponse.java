package com.co.cetus.pruebatecnica.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

    private Integer id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String estado;
    private String nombreLaboratorio;
}