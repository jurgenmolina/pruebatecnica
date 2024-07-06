package com.co.cetus.pruebatecnica.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Integer id;
    private String username;
    private String nombre;
    private String tipoDocumento;
    private String documento;
    private String email;
    private String telefono;
    private List<RolResponse> roles;
}
