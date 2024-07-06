package com.co.cetus.pruebatecnica.service;

import com.co.cetus.pruebatecnica.model.request.ProductoRequest;
import com.co.cetus.pruebatecnica.model.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    ProductoResponse crearProducto(ProductoRequest productoRequest);

    ProductoResponse obtenerProductoPorId(Integer id);

    ProductoResponse obtenerProductoPorCodigo(String codigo);

    List<ProductoResponse> obtenerTodosLosProductos();

    List<ProductoResponse> obtenerProductosPorEstado(String estado);

    ProductoResponse actualizarProducto(Integer id, ProductoRequest productoRequest);

    void eliminarProducto(Integer id);

    boolean existeProductoPorCodigo(String codigo);
}