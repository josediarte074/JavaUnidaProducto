package com.jose.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.inventario.modelo.ProductoModule;
import com.jose.inventario.service.ProductoService;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ProductoModule createProducto(@RequestBody ProductoModule producto){
        return productoService.crearProducto(producto);

    }

    @GetMapping
    public List<ProductoModule> getAllProducto(){
        return productoService.getAllProducto();
    }

    @GetMapping("{id}")
    public ProductoModule searchProductoById(@PathVariable("id") Integer id){
        return productoService.getProductoById(id);
    }

    @DeleteMapping("{id}")
    public void deleteProductoById(@PathVariable("id") Integer id){
        productoService.deleteProducto(id);
    }
}