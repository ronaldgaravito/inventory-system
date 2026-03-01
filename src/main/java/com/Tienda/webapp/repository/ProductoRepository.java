package com.Tienda.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tienda.webapp.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, String> {

    List<Producto> findByStockLessThan(int stock);

    List<Producto> findByEstadoProducto(String estado);

}
