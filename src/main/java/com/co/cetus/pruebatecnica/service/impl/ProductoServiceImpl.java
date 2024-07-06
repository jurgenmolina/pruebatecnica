package com.co.cetus.pruebatecnica.service.impl;

import com.co.cetus.pruebatecnica.model.entity.Producto;
import com.co.cetus.pruebatecnica.model.request.ProductoRequest;
import com.co.cetus.pruebatecnica.model.response.ProductoResponse;
import com.co.cetus.pruebatecnica.repository.ProductoRepository;
import com.co.cetus.pruebatecnica.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductoResponse crearProducto(ProductoRequest productoRequest) {
        try {
            Producto producto = modelMapper.map(productoRequest, Producto.class);
            Producto productoGuardado = productoRepository.save(producto);
            logger.info("Producto creado con éxito. ID: {}", productoGuardado.getId());
            return modelMapper.map(productoGuardado, ProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al crear el producto: {}", e.getMessage());
            throw new RuntimeException("Error al crear el producto", e);
        }
    }

    @Override
    public ProductoResponse obtenerProductoPorId(Integer id) {
        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
            logger.info("Producto encontrado. ID: {}", id);
            return modelMapper.map(producto, ProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al obtener el producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al obtener el producto", e);
        }
    }

    @Override
    public ProductoResponse obtenerProductoPorCodigo(String codigo) {
        try {
            Producto producto = productoRepository.findByCodigo(codigo)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + codigo));
            logger.info("Producto encontrado. Código: {}", codigo);
            return modelMapper.map(producto, ProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al obtener el producto con código {}: {}", codigo, e.getMessage());
            throw new RuntimeException("Error al obtener el producto", e);
        }
    }

    @Override
    public List<ProductoResponse> obtenerTodosLosProductos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            logger.info("Se obtuvieron {} productos", productos.size());
            return productos.stream()
                    .map(producto -> modelMapper.map(producto, ProductoResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener todos los productos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los productos", e);
        }
    }

    @Override
    public List<ProductoResponse> obtenerProductosPorEstado(String estado) {
        try {
            List<Producto> productos = productoRepository.findByEstado(estado);
            logger.info("Se obtuvieron {} productos con estado: {}", productos.size(), estado);
            return productos.stream()
                    .map(producto -> modelMapper.map(producto, ProductoResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener productos por estado {}: {}", estado, e.getMessage());
            throw new RuntimeException("Error al obtener los productos por estado", e);
        }
    }

    @Override
    public ProductoResponse actualizarProducto(Integer id, ProductoRequest productoRequest) {
        try {
            Producto productoExistente = productoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

            modelMapper.map(productoRequest, productoExistente);
            Producto productoActualizado = productoRepository.save(productoExistente);
            logger.info("Producto actualizado con éxito. ID: {}", id);
            return modelMapper.map(productoActualizado, ProductoResponse.class);
        } catch (Exception e) {
            logger.error("Error al actualizar el producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar el producto", e);
        }
    }

    @Override
    public void eliminarProducto(Integer id) {
        try {
            if (!productoRepository.existsById(id)) {
                throw new RuntimeException("Producto no encontrado con id: " + id);
            }
            productoRepository.deleteById(id);
            logger.info("Producto eliminado con éxito. ID: {}", id);
        } catch (Exception e) {
            logger.error("Error al eliminar el producto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el producto", e);
        }
    }

    @Override
    public boolean existeProductoPorCodigo(String codigo) {
        try {
            boolean existe = productoRepository.existsByCodigo(codigo);
            logger.info("Verificación de existencia de producto con código {}: {}", codigo, existe);
            return existe;
        } catch (Exception e) {
            logger.error("Error al verificar la existencia del producto con código {}: {}", codigo, e.getMessage());
            throw new RuntimeException("Error al verificar la existencia del producto", e);
        }
    }
}