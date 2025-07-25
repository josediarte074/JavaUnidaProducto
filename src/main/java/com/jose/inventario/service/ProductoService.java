package com.jose.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jose.inventario.modelo.ProductoModule;
import com.jose.inventario.repository.ProductoRepository;

@Service
@Component
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoModule crearProducto(ProductoModule producto){
       return productoRepository.save(producto) ;
    }

    public ProductoModule getProductoById(Integer id){
        Optional<ProductoModule> optionalProducto = productoRepository.findById(id);
        return optionalProducto.get();

    }

    public List<ProductoModule> getAllProducto(){
        return productoRepository.findAll();
    }

    public void deleteProducto(Integer id){
        productoRepository.deleteById(id);
    }
    
}
