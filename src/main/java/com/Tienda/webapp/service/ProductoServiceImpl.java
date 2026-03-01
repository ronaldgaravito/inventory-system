package com.Tienda.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    // Inyectamos el repositorio
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listarProductos() {
        // Aquí podrías poner lógica extra, como: "Solo activos" o "Ordenar por precio"
        return productoRepository.findAll();
    }

    @Override
    public Producto buscarPorId(String id) {
        return productoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Override
    public void eliminarProducto(String id) {
        productoRepository.deleteById(id);
    }
}
