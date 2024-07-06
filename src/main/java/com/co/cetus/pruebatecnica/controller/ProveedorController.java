package com.co.cetus.pruebatecnica.controller;

import com.co.cetus.pruebatecnica.model.request.ProveedorRequest;
import com.co.cetus.pruebatecnica.model.response.ProveedorResponse;
import com.co.cetus.pruebatecnica.service.ProveedorService;
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
@RequestMapping("/api/proveedor")
@RequiredArgsConstructor
public class ProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorController.class);

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponse> crearProveedor(@Valid @RequestBody ProveedorRequest proveedorRequest) {
        try {
            ProveedorResponse proveedorCreado = proveedorService.crearProveedor(proveedorRequest);
            logger.info("Proveedor creado exitosamente con ID: {}", proveedorCreado.getId());
            return new ResponseEntity<>(proveedorCreado, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error al crear el proveedor: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtenerProveedorPorId(@PathVariable Integer id) {
        try {
            ProveedorResponse proveedor = proveedorService.obtenerProveedorPorId(id);
            logger.info("Proveedor encontrado con ID: {}", id);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            logger.error("Error al obtener el proveedor con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> obtenerTodosLosProveedores() {
        try {
            List<ProveedorResponse> proveedores = proveedorService.obtenerTodosLosProveedores();
            logger.info("Se obtuvieron {} proveedores", proveedores.size());
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            logger.error("Error al obtener todos los proveedores: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponse> actualizarProveedor(@PathVariable Integer id, @Valid @RequestBody ProveedorRequest proveedorRequest) {
        try {
            ProveedorResponse proveedorActualizado = proveedorService.actualizarProveedor(id, proveedorRequest);
            logger.info("Proveedor actualizado exitosamente con ID: {}", id);
            return ResponseEntity.ok(proveedorActualizado);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el proveedor con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Integer id) {
        try {
            proveedorService.eliminarProveedor(id);
            logger.info("Proveedor eliminado exitosamente con ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el proveedor con ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/identificacion/{numeroIdentificacion}")
    public ResponseEntity<ProveedorResponse> obtenerProveedorPorNumeroIdentificacion(@PathVariable String numeroIdentificacion) {
        try {
            ProveedorResponse proveedor = proveedorService.obtenerProveedorPorNumeroIdentificacion(numeroIdentificacion);
            logger.info("Proveedor encontrado con número de identificación: {}", numeroIdentificacion);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            logger.error("Error al obtener el proveedor con número de identificación {}: {}", numeroIdentificacion, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/existe/{numeroIdentificacion}")
    public ResponseEntity<Boolean> existeProveedorPorNumeroIdentificacion(@PathVariable String numeroIdentificacion) {
        try {
            boolean existe = proveedorService.existeProveedorPorNumeroIdentificacion(numeroIdentificacion);
            logger.info("Verificación de existencia de proveedor con número de identificación {}: {}", numeroIdentificacion, existe);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            logger.error("Error al verificar la existencia del proveedor con número de identificación {}: {}", numeroIdentificacion, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}