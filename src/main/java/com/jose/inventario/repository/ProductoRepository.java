package com.jose.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jose.inventario.modelo.ProductoModule;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModule, Integer> {

}
