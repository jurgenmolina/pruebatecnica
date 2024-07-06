package com.co.cetus.pruebatecnica.repository;

import com.co.cetus.pruebatecnica.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    
    Optional<Proveedor> findByIdentificacion(String identificacion);
    
    boolean existsByIdentificacion(String identificacion);
}