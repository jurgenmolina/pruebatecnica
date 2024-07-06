package com.co.cetus.pruebatecnica.repository;

import com.co.cetus.pruebatecnica.model.entity.RecepcionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecepcionProductoRepository extends JpaRepository<RecepcionProducto, Integer> {
    
    List<RecepcionProducto> findByProveedorId(Integer proveedorId);
    
    List<RecepcionProducto> findByProductoId(Integer productoId);
    
    List<RecepcionProducto> findByFechaHoraRecepcionBetween(LocalDateTime inicio, LocalDateTime fin);
}