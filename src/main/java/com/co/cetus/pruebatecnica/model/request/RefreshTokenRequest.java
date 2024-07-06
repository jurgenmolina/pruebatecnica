package com.co.cetus.pruebatecnica.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "El token de refresco no puede estar vacío")
    private String refreshToken;
}