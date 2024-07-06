package com.co.cetus.pruebatecnica.service;

import com.co.cetus.pruebatecnica.model.request.ProveedorRequest;
import com.co.cetus.pruebatecnica.model.response.ProveedorResponse;

import java.util.List;

public interface ProveedorService {

    ProveedorResponse crearProveedor(ProveedorRequest proveedorRequest);

    ProveedorResponse obtenerProveedorPorId(Integer id);

    ProveedorResponse obtenerProveedorPorNumeroIdentificacion(String numeroIdentificacion);

    List<ProveedorResponse> obtenerTodosLosProveedores();

    ProveedorResponse actualizarProveedor(Integer id, ProveedorRequest proveedorRequest);

    void eliminarProveedor(Integer id);

    boolean existeProveedorPorNumeroIdentificacion(String numeroIdentificacion);
}