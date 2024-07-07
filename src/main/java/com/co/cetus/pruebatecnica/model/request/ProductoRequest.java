package com.co.cetus.pruebatecnica.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {

    @NotBlank(message = "El código no puede estar vacío")
    private String codigo;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "^(ACTIVO|INACTIVO)$", message = "Estado inválido")
    private String estado;

    @NotBlank(message = "El nombre del laboratorio no puede estar vacío")
    private String nombreLaboratorio;
}