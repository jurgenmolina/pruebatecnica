package com.co.cetus.pruebatecnica.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponse {

    private Integer id;
    private String tipoIdentificacion;
    private String identificacion;
    private String razonSocial;
    private String direccion;
    private String nombreContacto;
    private String celularContacto;
}