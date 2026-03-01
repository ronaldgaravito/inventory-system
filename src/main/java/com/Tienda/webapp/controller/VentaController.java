package com.Tienda.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tienda.webapp.model.DetalleVenta;
import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.model.Usuario;
import com.Tienda.webapp.model.Venta;
import com.Tienda.webapp.service.VentaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarCompra(@RequestBody Map<String, Object> request, HttpSession session) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null) {
                return ResponseEntity.status(401).body(Map.of("success", false, "message", "Debes iniciar sesión para comprar"));
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> carrito = (List<Map<String, Object>>) request.get("carrito");
            Number totalNum = (Number) request.get("total");
            Integer total = totalNum.intValue();

            Venta venta = new Venta();
            venta.setUsuario(usuario);
            venta.setTotalVenta(total);

            List<DetalleVenta> detalles = new ArrayList<>();
            for (Map<String, Object> item : carrito) {
                DetalleVenta detalle = new DetalleVenta();
                
                Producto producto = new Producto();
                producto.setIdProducto((String) item.get("id"));
                detalle.setProducto(producto);
                
                Number cantidadNum = (Number) item.get("cantidad");
                detalle.setCantidad(cantidadNum.intValue());
                
                Number precioNum = (Number) item.get("precio");
                detalle.setSubtotal(precioNum.intValue() * detalle.getCantidad());
                
                detalles.add(detalle);
            }

            ventaService.finalizarVenta(venta, detalles);

            return ResponseEntity.ok(Map.of("success", true, "message", "Compra realizada con éxito"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Error al procesar la compra: " + e.getMessage()));
        }
    }
}
