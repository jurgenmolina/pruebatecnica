package com.co.cetus.pruebatecnica.service;

import com.co.cetus.pruebatecnica.model.request.RecepcionProductoRequest;
import com.co.cetus.pruebatecnica.model.response.RecepcionProductoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface RecepcionProductoService {

    RecepcionProductoResponse crearRecepcionProducto(RecepcionProductoRequest request);

    RecepcionProductoResponse obtenerRecepcionProductoPorId(Integer id);

    List<RecepcionProductoResponse> obtenerTodasLasRecepcionesProducto();

    List<RecepcionProductoResponse> obtenerRecepcionesPorProveedorId(Integer proveedorId);

    List<RecepcionProductoResponse> obtenerRecepcionesPorProductoId(Integer productoId);

    List<RecepcionProductoResponse> obtenerRecepcionesPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);

    RecepcionProductoResponse actualizarRecepcionProducto(Integer id, RecepcionProductoRequest request);

    void eliminarRecepcionProducto(Integer id);
}