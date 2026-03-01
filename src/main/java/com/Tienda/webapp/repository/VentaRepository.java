package com.Tienda.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tienda.webapp.model.Usuario;
import com.Tienda.webapp.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, String> {
    List<Venta> findByUsuarioOrderByFechaDesc(Usuario usuario);
}
