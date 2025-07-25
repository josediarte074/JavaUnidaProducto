package com.jose.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


    //Crear Productos
    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody ProductoModule producto) {
        if (producto.getStock() == null || producto.getStock() < 0) {
            return ResponseEntity.badRequest().body("Stock no puede ser negativo o nulo");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            return ResponseEntity.badRequest().body("Precio debe ser mayor a 0");
        }
        ProductoModule productoCreado = productoService.crearProducto(producto);
        return ResponseEntity.ok(productoCreado);
    }
    //LIstar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoModule>> getAllProducto() {
        return ResponseEntity.ok(productoService.getAllProducto());
    }
    //Buscar Producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> searchProductoById(@PathVariable("id") Integer id) {
        try {
            ProductoModule producto = productoService.getProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con ID: " + id);
        }
    }
    //Actualizar Producto
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable("id") Integer id,
            @RequestBody ProductoModule productoActualizado) {
        if (productoActualizado.getStock() != null && productoActualizado.getStock() < 0) {
            return ResponseEntity.badRequest().body("Stock no puede ser negativo");
        }

        try {
            ProductoModule productoActualizadoDB = productoService.updateProducto(id, productoActualizado);
            return ResponseEntity.ok(productoActualizadoDB);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //ELIMINAR PRODUCTO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductoById(@PathVariable("id") Integer id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
    //Consultar estadisticas
    @GetMapping("/estadisticas")
    public ResponseEntity<?> getEstadisticas() {
        var estadisticas = productoService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }
}