package com.co.cetus.pruebatecnica.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorRequest {

    @NotBlank(message = "El tipo de identificación no puede estar vacío")
    @Pattern(regexp = "^(Cedula de Ciudadania|NIT|Cedula de Extranjería|NIT de Extranjería)$", message = "Tipo de identificación inválido")
    private String tipoIdentificacion;

    @NotBlank(message = "El número de identificación no puede estar vacío")
    private String identificacion;

    @NotBlank(message = "El nombre o razón social no puede estar vacío")
    private String razonSocial;

    private String direccion;

    private String nombreContacto;

    @Pattern(regexp = "^\\d{10}$", message = "El número de celular debe tener 10 dígitos")
    private String celularContacto;
}