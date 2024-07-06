package com.co.cetus.pruebatecnica.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionProductoRequest {

    @NotNull(message = "La fecha y hora de recepción no puede estar vacía")
    private LocalDateTime fechaHoraRecepcion;

    @NotNull(message = "El ID del producto no puede estar vacío")
    private Integer idProducto;

    @NotNull(message = "El ID del proveedor no puede estar vacío")
    private Integer idProveedor;

    @NotBlank(message = "El número de factura no puede estar vacío")
    private String numeroFactura;

    @NotNull(message = "La cantidad no puede estar vacía")
    @Positive(message = "La cantidad debe ser un número positivo")
    private Integer cantidad;

    @NotBlank(message = "El lote no puede estar vacío")
    private String lote;

    @NotBlank(message = "El registro INVIMA no puede estar vacío")
    private String registroInvima;

    @NotNull(message = "La fecha de vencimiento no puede estar vacía")
    private LocalDateTime fechaVencimiento;

    private String estadoPresentacion;

    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Integer idUsuario;
}