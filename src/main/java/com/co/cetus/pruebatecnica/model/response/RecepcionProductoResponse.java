package com.co.cetus.pruebatecnica.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionProductoResponse {

    private Integer id;
    private LocalDateTime fechaHoraRecepcion;
    private ProductoResponse producto;
    private ProveedorResponse proveedor;
    private String numeroFactura;
    private Integer cantidad;
    private String lote;
    private String registroInvima;
    private LocalDateTime fechaVencimiento;
    private String estadoPresentacion;
}