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

    public ProductoModule crearProducto(ProductoModule producto) {
    producto.setEstado(
        (producto.getStock() != null && producto.getStock() > 0) ? "Disponible" : "Agotado"
    );
    return productoRepository.save(producto);
}

    public ProductoModule getProductoById(Integer id) {
        Optional<ProductoModule> optionalProducto = productoRepository.findById(id);
        return optionalProducto.get();

    }

    public List<ProductoModule> getAllProducto() {
        return productoRepository.findAll();
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public ProductoModule updateProducto(Integer id, ProductoModule productoActualizado) {

        ProductoModule productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Actualizar campos
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setEstado(productoActualizado.getEstado());

        return productoRepository.save(productoExistente);
    }

    public EstadisticasProductos obtenerEstadisticas() {
        List<ProductoModule> productos = productoRepository.findAll();
        int totalProductos = productos.size();
        double sumaPrecios = productos.stream()
                .mapToDouble(p -> p.getPrecio() != null ? p.getPrecio() : 0)
                .sum();
        double promedioPrecios = totalProductos > 0 ? sumaPrecios / totalProductos : 0;

        long disponibles = productos.stream()
                .filter(p -> "Disponible".equalsIgnoreCase(p.getEstado()))
                .count();

        long agotados = productos.stream()
                .filter(p -> "Agotado".equalsIgnoreCase(p.getEstado()))
                .count();

        return new EstadisticasProductos(totalProductos, promedioPrecios, (int) disponibles, (int) agotados);
    }

    // Clase interna en el service (o podés crear aparte)
    public static class EstadisticasProductos {
        private int totalProductos;
        private double promedioPrecios;
        private int productosDisponibles;
        private int productosAgotados;

        public EstadisticasProductos(int totalProductos, double promedioPrecios, int productosDisponibles,
                int productosAgotados) {
            this.totalProductos = totalProductos;
            this.promedioPrecios = promedioPrecios;
            this.productosDisponibles = productosDisponibles;
            this.productosAgotados = productosAgotados;
        }


          public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public double getPromedioPrecios() {
        return promedioPrecios;
    }

    public void setPromedioPrecios(double promedioPrecios) {
        this.promedioPrecios = promedioPrecios;
    }

    public int getProductosDisponibles() {
        return productosDisponibles;
    }

    public void setProductosDisponibles(int productosDisponibles) {
        this.productosDisponibles = productosDisponibles;
    }

    public int getProductosAgotados() {
        return productosAgotados;
    }

    public void setProductosAgotados(int productosAgotados) {
        this.productosAgotados = productosAgotados;
    }
    }
    

}
