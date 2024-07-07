package com.co.cetus.pruebatecnica.service.impl;

import com.co.cetus.pruebatecnica.model.entity.RecepcionProducto;
import com.co.cetus.pruebatecnica.model.entity.Producto;
import com.co.cetus.pruebatecnica.model.entity.Proveedor;
import com.co.cetus.pruebatecnica.model.entity.Usuario;
import com.co.cetus.pruebatecnica.model.request.RecepcionProductoRequest;
import com.co.cetus.pruebatecnica.model.response.RecepcionProductoResponse;
import com.co.cetus.pruebatecnica.repository.RecepcionProductoRepository;
import com.co.cetus.pruebatecnica.repository.ProductoRepository;
import com.co.cetus.pruebatecnica.repository.ProveedorRepository;
import com.co.cetus.pruebatecnica.repository.UsuarioRepository;
import com.co.cetus.pruebatecnica.service.RecepcionProductoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecepcionProductoServiceImpl implements RecepcionProductoService {

    private static final Logger logger = LoggerFactory.getLogger(RecepcionProductoServiceImpl.class);

    private final RecepcionProductoRepository recepcionProductoRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    
    @Override
    @Transactional
    public RecepcionProductoResponse crearRecepcionProducto(RecepcionProductoRequest request) {
        try {
            Producto producto = productoRepository.findById(request.getProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + request.getProducto()));

            Proveedor proveedor = proveedorRepository.findById(request.getProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + request.getProveedor()));


            RecepcionProducto recepcionProducto = modelMapper.map(request, RecepcionProducto.class);
            recepcionProducto.setProducto(producto);
            recepcionProducto.setProveedor(proveedor);

            RecepcionProducto recepcionGuardada = recepcionProductoRepository.save(recepcionProducto);
            logger.info("Recepción de producto creada con éxito. ID: {}", recepcionGuardada.getId());
            return modelMapper.map(recepcionGuardada, RecepcionProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al crear la recepción de producto: {}", e.getMessage());
            throw new RuntimeException("Error al crear la recepción de producto", e);
        }
    }

    @Override
    public RecepcionProductoResponse obtenerRecepcionProductoPorId(Integer id) {
        try {
            RecepcionProducto recepcion = recepcionProductoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recepción de producto no encontrada con id: " + id));
            logger.info("Recepción de producto encontrada. ID: {}", id);
            return modelMapper.map(recepcion, RecepcionProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al obtener la recepción de producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener la recepción de producto", e);
        }
    }

    @Override
    public List<RecepcionProductoResponse> obtenerTodasLasRecepcionesProducto() {
        try {
            List<RecepcionProducto> recepciones = recepcionProductoRepository.findAll();
            logger.info("Se obtuvieron {} recepciones de producto", recepciones.size());
            return recepciones.stream()
                    .map(recepcion -> modelMapper.map(recepcion, RecepcionProductoResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener todas las recepciones de producto: {}", e.getMessage());
            throw new RuntimeException("Error al obtener las recepciones de producto", e);
        }
    }

    @Override
    public List<RecepcionProductoResponse> obtenerRecepcionesPorProveedorId(Integer proveedorId) {
        try {
            List<RecepcionProducto> recepciones = recepcionProductoRepository.findByProveedorId(proveedorId);
            logger.info("Se obtuvieron {} recepciones de producto para el proveedor ID: {}", recepciones.size(), proveedorId);
            return recepciones.stream()
                    .map(recepcion -> modelMapper.map(recepcion, RecepcionProductoResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener las recepciones de producto para el proveedor ID {}: {}", proveedorId, e.getMessage());
            throw new RuntimeException("Error al obtener las recepciones de producto por proveedor", e);
        }
    }

    @Override
    public List<RecepcionProductoResponse> obtenerRecepcionesPorProductoId(Integer productoId) {
        try {
            List<RecepcionProducto> recepciones = recepcionProductoRepository.findByProductoId(productoId);
            logger.info("Se obtuvieron {} recepciones para el producto ID: {}", recepciones.size(), productoId);
            return recepciones.stream()
                    .map(recepcion -> modelMapper.map(recepcion, RecepcionProductoResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener las recepciones de producto para el producto ID {}: {}", productoId, e.getMessage());
            throw new RuntimeException("Error al obtener las recepciones de producto por producto", e);
        }
    }

    @Override
    public List<RecepcionProductoResponse> obtenerRecepcionesPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        try {
            List<RecepcionProducto> recepciones = recepcionProductoRepository.findByFechaHoraRecepcionBetween(inicio, fin);
            logger.info("Se obtuvieron {} recepciones de producto entre {} y {}", recepciones.size(), inicio, fin);
            return recepciones.stream()
                    .map(recepcion -> modelMapper.map(recepcion, RecepcionProductoResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener las recepciones de producto entre {} y {}: {}", inicio, fin, e.getMessage());
            throw new RuntimeException("Error al obtener las recepciones de producto por rango de fechas", e);
        }
    }

    @Override
    @Transactional
    public RecepcionProductoResponse actualizarRecepcionProducto(Integer id, RecepcionProductoRequest request) {
        try {
            RecepcionProducto recepcionExistente = recepcionProductoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Recepción de producto no encontrada con id: " + id));

            Producto producto = productoRepository.findById(request.getProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + request.getProducto()));

            Proveedor proveedor = proveedorRepository.findById(request.getProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + request.getProveedor()));

            modelMapper.map(request, recepcionExistente);
            recepcionExistente.setProducto(producto);
            recepcionExistente.setProveedor(proveedor);

            RecepcionProducto recepcionActualizada = recepcionProductoRepository.save(recepcionExistente);
            logger.info("Recepción de producto actualizada con éxito. ID: {}", id);
            return modelMapper.map(recepcionActualizada, RecepcionProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al actualizar la recepción de producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar la recepción de producto", e);
        }
    }

    @Override
    @Transactional
    public void eliminarRecepcionProducto(Integer id) {
        try {
            if (!recepcionProductoRepository.existsById(id)) {
                throw new RuntimeException("Recepción de producto no encontrada con id: " + id);
            }
            recepcionProductoRepository.deleteById(id);
            logger.info("Recepción de producto eliminada con éxito. ID: {}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar la recepción de producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar la recepción de producto", e);
        }
    }
}