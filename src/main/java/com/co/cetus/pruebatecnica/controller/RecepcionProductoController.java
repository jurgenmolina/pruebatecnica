package com.co.cetus.pruebatecnica.controller;

import com.co.cetus.pruebatecnica.model.request.RecepcionProductoRequest;
import com.co.cetus.pruebatecnica.model.response.RecepcionProductoResponse;
import com.co.cetus.pruebatecnica.service.RecepcionProductoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/recepcion-producto")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecepcionProductoController {

    private static final Logger logger = LoggerFactory.getLogger(RecepcionProductoController.class);

    private final RecepcionProductoService recepcionProductoService;

    @PostMapping
    public ResponseEntity<RecepcionProductoResponse> crearRecepcionProducto(@Valid @RequestBody RecepcionProductoRequest request) {
        try {
            RecepcionProductoResponse recepcionCreada = recepcionProductoService.crearRecepcionProducto(request);
            logger.info("Recepción de producto creada exitosamente con ID: {}", recepcionCreada.getId());
            return new ResponseEntity<>(recepcionCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al crear la recepción de producto: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionProductoResponse> obtenerRecepcionProductoPorId(@PathVariable Integer id) {
        try {
            RecepcionProductoResponse recepcion = recepcionProductoService.obtenerRecepcionProductoPorId(id);
            logger.info("Recepción de producto encontrada con ID: {}", id);
            return ResponseEntity.ok(recepcion);
        } catch (RuntimeException e) {
            logger.error("Error al obtener la recepción de producto con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<RecepcionProductoResponse>> obtenerTodasLasRecepcionesProducto() {
        try {
            List<RecepcionProductoResponse> recepciones = recepcionProductoService.obtenerTodasLasRecepcionesProducto();
            logger.info("Se obtuvieron {} recepciones de producto", recepciones.size());
            return ResponseEntity.ok(recepciones);
        } catch (Exception e) {
            logger.error("Error al obtener todas las recepciones de producto: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<RecepcionProductoResponse>> obtenerRecepcionesPorProveedorId(@PathVariable Integer proveedorId) {
        try {
            List<RecepcionProductoResponse> recepciones = recepcionProductoService.obtenerRecepcionesPorProveedorId(proveedorId);
            logger.info("Se obtuvieron {} recepciones para el proveedor ID: {}", recepciones.size(), proveedorId);
            return ResponseEntity.ok(recepciones);
        } catch (Exception e) {
            logger.error("Error al obtener las recepciones de producto para el proveedor ID {}: {}", proveedorId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<RecepcionProductoResponse>> obtenerRecepcionesPorProductoId(@PathVariable Integer productoId) {
        try {
            List<RecepcionProductoResponse> recepciones = recepcionProductoService.obtenerRecepcionesPorProductoId(productoId);
            logger.info("Se obtuvieron {} recepciones para el producto ID: {}", recepciones.size(), productoId);
            return ResponseEntity.ok(recepciones);
        } catch (Exception e) {
            logger.error("Error al obtener las recepciones de producto para el producto ID {}: {}", productoId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<RecepcionProductoResponse>> obtenerRecepcionesPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        try {
            List<RecepcionProductoResponse> recepciones = recepcionProductoService.obtenerRecepcionesPorRangoFechas(inicio, fin);
            logger.info("Se obtuvieron {} recepciones entre {} y {}", recepciones.size(), inicio, fin);
            return ResponseEntity.ok(recepciones);
        } catch (Exception e) {
            logger.error("Error al obtener las recepciones de producto entre {} y {}: {}", inicio, fin, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionProductoResponse> actualizarRecepcionProducto(@PathVariable Integer id, @Valid @RequestBody RecepcionProductoRequest request) {
        try {
            RecepcionProductoResponse recepcionActualizada = recepcionProductoService.actualizarRecepcionProducto(id, request);
            logger.info("Recepción de producto actualizada exitosamente con ID: {}", id);
            return ResponseEntity.ok(recepcionActualizada);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar la recepción de producto con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecepcionProducto(@PathVariable Integer id) {
        try {
            recepcionProductoService.eliminarRecepcionProducto(id);
            logger.info("Recepción de producto eliminada exitosamente con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error al eliminar la recepción de producto con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}