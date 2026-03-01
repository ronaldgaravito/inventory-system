package com.Tienda.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Tienda.webapp.model.Compra;
import com.Tienda.webapp.model.DetalleCompra;
import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.model.Proveedor;
import com.Tienda.webapp.model.Usuario;
import com.Tienda.webapp.service.CompraService;
import com.Tienda.webapp.service.ProductoService;
import com.Tienda.webapp.service.ProveedorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/admin/pedido-compra")
    public String verPaginaPedido(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null || u.getIdRol() != 1) {
            return "redirect:/";
        }

        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("productos", productoService.listarProductos());
        return "pedido-compra";
    }

    @PostMapping("/api/compras/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarCompra(@RequestBody Map<String, Object> request, HttpSession session) {
        try {
            Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
            if (u == null || u.getIdRol() != 1) {
                return ResponseEntity.status(403).body(Map.of("success", false, "message", "Acceso denegado"));
            }

            String idProveedor = (String) request.get("idProveedor");
            Number totalNum = (Number) request.get("total");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("detalles");

            Proveedor proveedor = proveedorService.buscarPorId(idProveedor);
            if (proveedor == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Proveedor no encontrado"));
            }

            Compra compra = new Compra();
            compra.setProveedor(proveedor);
            compra.setTotalCompra(totalNum.intValue());

            List<DetalleCompra> detalles = new ArrayList<>();
            for (Map<String, Object> item : items) {
                DetalleCompra detalle = new DetalleCompra();
                
                String idProd = (String) item.get("idProducto");
                Number cantNum = (Number) item.get("cantidad");
                Number subNum = (Number) item.get("subtotal");

                Producto p = new Producto();
                p.setIdProducto(idProd);
                detalle.setProducto(p);
                detalle.setCantidad(cantNum.intValue());
                detalle.setSubtotal(subNum.intValue());

                detalles.add(detalle);
            }

            compraService.guardarCompra(compra, detalles);

            return ResponseEntity.ok(Map.of("success", true, "message", "Pedido de compra registrado con éxito. ¡Stock actualizado!"));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Error al procesar el pedido: " + e.getMessage()));
        }
    }

    @GetMapping("/admin/historial-compras")
    public String verHistorialCompras(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null || u.getIdRol() != 1) {
            return "redirect:/";
        }

        model.addAttribute("compras", compraService.listarTodas());
        return "historial-compras";
    }
}
