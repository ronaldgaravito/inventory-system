package com.Tienda.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tienda.webapp.model.DetalleCompra;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, String> {
}
