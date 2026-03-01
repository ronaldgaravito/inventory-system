package com.Tienda.webapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Tienda.webapp.model.DetalleVenta;
import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.model.Venta;
import com.Tienda.webapp.repository.DetalleVentaRepository;
import com.Tienda.webapp.repository.ProductoRepository;
import com.Tienda.webapp.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private DetalleVentaRepository detalleRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Transactional
    public Venta finalizarVenta(Venta venta, List<DetalleVenta> detalles) {
        // 1. Generar ID si no existe (V-XXXXX)
        if (venta.getIdVenta() == null || venta.getIdVenta().isEmpty()) {
            venta.setIdVenta(generarId("V"));
        }
        
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }

        // 2. Guardar la Venta
        Venta ventaGuardada = ventaRepo.save(venta);

        // 3. Procesar y guardar detalles
        for (DetalleVenta detalle : detalles) {
            detalle.setVenta(ventaGuardada);
            
            if (detalle.getIdDetalle() == null || detalle.getIdDetalle().isEmpty()) {
                detalle.setIdDetalle(generarId("D"));
            }

            // Actualizar stock del producto
            Producto product = productoRepo.findById(detalle.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getIdProducto()));
            
            if (product.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getDescripcion());
            }
            
            product.setStock(product.getStock() - detalle.getCantidad());
            productoRepo.save(product);

            detalleRepo.save(detalle);
        }

        return ventaGuardada;
    }

    private String generarId(String prefijo) {
        // Generar un ID corto de 10 caracteres (prefijo + random)
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 10 - prefijo.length() - 1);
        return prefijo + "-" + randomPart.toUpperCase();
    }
}
