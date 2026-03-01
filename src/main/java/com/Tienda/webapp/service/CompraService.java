package com.Tienda.webapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Tienda.webapp.model.Compra;
import com.Tienda.webapp.model.DetalleCompra;
import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.repository.CompraRepository;
import com.Tienda.webapp.repository.DetalleCompraRepository;
import com.Tienda.webapp.repository.ProductoRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepo;

    @Autowired
    private DetalleCompraRepository detalleRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Transactional
    public Compra guardarCompra(Compra compra, List<DetalleCompra> detalles) {
        // 1. Generar ID de compra (C-XXXXX)
        String idCompra = "C-" + (10000 + new Random().nextInt(90000));
        compra.setIdCompra(idCompra);
        compra.setFecha(LocalDate.now());

        // 2. Guardar la compra cabecera
        Compra compraGuardada = compraRepo.save(compra);

        // 3. Procesar los detalles e incrementar el stock
        for (DetalleCompra detalle : detalles) {
            detalle.setIdDetalle("DC-" + (100000 + new Random().nextInt(900000)));
            detalle.setCompra(compraGuardada);

            // Buscar el producto para actualizar su stock
            Producto product = productoRepo.findById(detalle.getProducto().getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalle.getProducto().getIdProducto()));
            
            // INCREMENTAR EL STOCK (Pedido al proveedor)
            product.setStock(product.getStock() + detalle.getCantidad());
            productoRepo.save(product);

            detalleRepo.save(detalle);
        }

        return compraGuardada;
    }

    @Transactional(readOnly = true)
    public List<Compra> listarTodas() {
        return compraRepo.findAllByOrderByFechaDesc();
    }
}
