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
    @Pattern(regexp = "^(CEDULA DE CIUDADANIA|NIT|CEDULA DE EXTRANJERIA)$", message = "Tipo de identificación inválido")
    private String tipoIdentificacion;

    @NotBlank(message = "El número de identificación no puede estar vacío")
    private String identificacion;

    @NotBlank(message = "El nombre o razón social no puede estar vacío")
    private String razonSocial;

    @NotBlank(message = "La direccion no puede estar vacía")
    private String direccion;

    @NotBlank(message = "El nombre de contacto no puede estar vacío")
    private String nombreContacto;

    @NotBlank(message = "El celular de contacto no puede estar vacío")
    private String celularContacto;
}