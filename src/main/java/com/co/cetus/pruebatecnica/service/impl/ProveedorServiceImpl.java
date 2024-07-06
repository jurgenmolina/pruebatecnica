package com.co.cetus.pruebatecnica.service.impl;

import com.co.cetus.pruebatecnica.model.entity.Proveedor;
import com.co.cetus.pruebatecnica.model.request.ProveedorRequest;
import com.co.cetus.pruebatecnica.model.response.ProveedorResponse;
import com.co.cetus.pruebatecnica.repository.ProveedorRepository;
import com.co.cetus.pruebatecnica.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    private final ProveedorRepository proveedorRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProveedorResponse crearProveedor(ProveedorRequest proveedorRequest) {
        try {
            Proveedor proveedor = modelMapper.map(proveedorRequest, Proveedor.class);
            Proveedor proveedorGuardado = proveedorRepository.save(proveedor);
            logger.info("Proveedor creado con éxito. ID: {}", proveedorGuardado.getId());
            return modelMapper.map(proveedorGuardado, ProveedorResponse.class);
        } catch (Exception e) {
            logger.error("Error al crear el proveedor: {}", e.getMessage());
            throw new RuntimeException("Error al crear el proveedor", e);
        }
    }

    @Override
    public ProveedorResponse obtenerProveedorPorId(Integer id) {
        try {
            Proveedor proveedor = proveedorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
            logger.info("Proveedor encontrado. ID: {}", id);
            return modelMapper.map(proveedor, ProveedorResponse.class);
        } catch (Exception e) {
            logger.error("Error al obtener el proveedor con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener el proveedor", e);
        }
    }

    @Override
    public ProveedorResponse obtenerProveedorPorNumeroIdentificacion(String numeroIdentificacion) {
        try {
            Proveedor proveedor = proveedorRepository.findByIdentificacion(numeroIdentificacion)
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con número de identificación: " + numeroIdentificacion));
            logger.info("Proveedor encontrado. Número de identificación: {}", numeroIdentificacion);
            return modelMapper.map(proveedor, ProveedorResponse.class);
        } catch (Exception e) {
            logger.error("Error al obtener el proveedor con número de identificación {}: {}", numeroIdentificacion, e.getMessage());
            throw new RuntimeException("Error al obtener el proveedor", e);
        }
    }

    @Override
    public List<ProveedorResponse> obtenerTodosLosProveedores() {
        try {
            List<Proveedor> proveedores = proveedorRepository.findAll();
            logger.info("Se obtuvieron {} proveedores", proveedores.size());
            return proveedores.stream()
                    .map(proveedor -> modelMapper.map(proveedor, ProveedorResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener todos los proveedores: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los proveedores", e);
        }
    }

    @Override
    public ProveedorResponse actualizarProveedor(Integer id, ProveedorRequest proveedorRequest) {
        try {
            Proveedor proveedorExistente = proveedorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
            
            modelMapper.map(proveedorRequest, proveedorExistente);
            Proveedor proveedorActualizado = proveedorRepository.save(proveedorExistente);
            logger.info("Proveedor actualizado con éxito. ID: {}", id);
            return modelMapper.map(proveedorActualizado, ProveedorResponse.class);
        } catch (Exception e) {
            logger.error("Error al actualizar el proveedor con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar el proveedor", e);
        }
    }

    @Override
    public void eliminarProveedor(Integer id) {
        try {
            if (!proveedorRepository.existsById(id)) {
                throw new RuntimeException("Proveedor no encontrado con id: " + id);
            }
            proveedorRepository.deleteById(id);
            logger.info("Proveedor eliminado con éxito. ID: {}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el proveedor con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el proveedor", e);
        }
    }

    @Override
    public boolean existeProveedorPorNumeroIdentificacion(String numeroIdentificacion) {
        try {
            boolean existe = proveedorRepository.existsByIdentificacion(numeroIdentificacion);
            logger.info("Verificación de existencia de proveedor con número de identificación {}: {}", numeroIdentificacion, existe);
            return existe;
        } catch (Exception e) {
            logger.error("Error al verificar la existencia del proveedor con número de identificación {}: {}", numeroIdentificacion, e.getMessage());
            throw new RuntimeException("Error al verificar la existencia del proveedor", e);
        }
    }
}