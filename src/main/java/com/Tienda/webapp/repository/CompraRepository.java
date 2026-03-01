package com.Tienda.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tienda.webapp.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, String> {
    List<Compra> findAllByOrderByFechaDesc();
}
