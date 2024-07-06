package com.co.cetus.pruebatecnica.repository;

import com.co.cetus.pruebatecnica.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    Optional<Producto> findByCodigo(String codigo);
    
    List<Producto> findByEstado(String estado);
    
    boolean existsByCodigo(String codigo);
}