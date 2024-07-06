package com.co.cetus.pruebatecnica.controller;

import com.co.cetus.pruebatecnica.model.request.ProductoRequest;
import com.co.cetus.pruebatecnica.model.response.ProductoResponse;
import com.co.cetus.pruebatecnica.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody ProductoRequest productoRequest) {
        try {
            ProductoResponse productoCreado = productoService.crearProducto(productoRequest);
            logger.info("Producto creado exitosamente con ID: {}", productoCreado.getId());
            return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al crear el producto: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProductoPorId(@PathVariable Integer id) {
        try {
            ProductoResponse producto = productoService.obtenerProductoPorId(id);
            logger.info("Producto encontrado con ID: {}", id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            logger.error("Error al obtener el producto con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> obtenerTodosLosProductos() {
        try {
            List<ProductoResponse> productos = productoService.obtenerTodosLosProductos();
            logger.info("Se obtuvieron {} productos", productos.size());
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            logger.error("Error al obtener todos los productos: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(@PathVariable Integer id, @Valid @RequestBody ProductoRequest productoRequest) {
        try {
            ProductoResponse productoActualizado = productoService.actualizarProducto(id, productoRequest);
            logger.info("Producto actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el producto con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        try {
            productoService.eliminarProducto(id);
            logger.info("Producto eliminado exitosamente con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el producto con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoResponse> obtenerProductoPorCodigo(@PathVariable String codigo) {
        try {
            ProductoResponse producto = productoService.obtenerProductoPorCodigo(codigo);
            logger.info("Producto encontrado con código: {}", codigo);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            logger.error("Error al obtener el producto con código {}: {}", codigo, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProductoResponse>> obtenerProductosPorEstado(@PathVariable String estado) {
        try {
            List<ProductoResponse> productos = productoService.obtenerProductosPorEstado(estado);
            logger.info("Se obtuvieron {} productos con estado: {}", productos.size(), estado);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            logger.error("Error al obtener productos con estado {}: {}", estado, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}