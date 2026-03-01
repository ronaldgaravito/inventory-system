package com.Tienda.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tienda.webapp.model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, String> {
}
