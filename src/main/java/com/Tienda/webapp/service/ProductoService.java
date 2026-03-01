package com.Tienda.webapp.service;

import java.util.List;

import com.Tienda.webapp.model.Producto;

public interface ProductoService {

    List<Producto> listarProductos();

    Producto buscarPorId(String id);
    
    Producto guardarProducto(Producto producto);
    
    void eliminarProducto(String id);
}
