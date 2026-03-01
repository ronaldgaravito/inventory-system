package com.Tienda.webapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Tienda.webapp.model.Producto;
import com.Tienda.webapp.repository.ProductoRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====== AUTO-CONFIGURANDO IMÁGENES PROFESIONALES ======");
        
        List<Producto> productos = productoRepository.findAll();
        
        // Verificar si existe algún producto de limpieza, si no, crear uno
        boolean tieneLimpieza = productos.stream().anyMatch(p -> p.getCategoria() != null && p.getCategoria().equals("limpieza"));
        if (!tieneLimpieza) {
            Producto detergente = new Producto();
            detergente.setIdProducto("LIMP001");
            detergente.setDescripcion("Detergente Líquido 1L");
            detergente.setPrecio(12500);
            detergente.setStock(20);
            detergente.setEstadoProducto("A");
            detergente.setCategoria("limpieza");
            detergente.setImagenUrl("https://images.unsplash.com/photo-1584622650111-993a426fbf0a?auto=format&fit=crop&w=400&h=300&q=80");
            productoRepository.save(detergente);
            System.out.println("Producto de limpieza creado: Detergente Líquido");
            productos = productoRepository.findAll(); // Recargar lista
        }

        for (Producto p : productos) {
            boolean updated = false;
            String desc = p.getDescripcion().toLowerCase();
            
            // Solo actualizamos si no tiene imagen o tiene la de relleno
            if (p.getImagenUrl() == null || p.getImagenUrl().isEmpty() || p.getImagenUrl().contains("via.placeholder")) {
                
                if (desc.contains("manzana")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1560806887-1e4cd0b6bcdb?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("frutas");
                    updated = true;
                } else if (desc.contains("arroz")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1586201375761-83865001e31c?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("granos");
                    updated = true;
                } else if (desc.contains("pasta") || desc.contains("dientes")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1559591937-e68fb3305ff4?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("higiene");
                    updated = true;
                } else if (desc.contains("huevo")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1582722872445-4195a0ef3f2f?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("lacteos");
                    updated = true;
                } else if (desc.contains("leche")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1550583724-125581fedade?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("lacteos");
                    updated = true;
                } else if (desc.contains("pan")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("panaderia");
                    updated = true;
                } else if (desc.contains("pollo") || desc.contains("pechuga")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1604503468506-a8da13d82791?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("carnes");
                    updated = true;
                } else if (desc.contains("queso")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1552767059-ce182ead6c1b?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("lacteos");
                    updated = true;
                } else if (desc.contains("banana") || desc.contains("banano")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1571771894821-ad9b58a3340e?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("frutas");
                    updated = true;
                } else if (desc.contains("jugo") || desc.contains("naranja")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1613478223719-2ab802602423?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("bebidas");
                    updated = true;
                } else if (desc.contains("detergente") || desc.contains("limpieza") || desc.contains("jabón")) {
                    p.setImagenUrl("https://images.unsplash.com/photo-1584622650111-993a426fbf0a?auto=format&fit=crop&w=400&h=300&q=80");
                    p.setCategoria("limpieza");
                    updated = true;
                }
            }
            
            if (updated) {
                productoRepository.save(p);
                System.out.println("Producto actualizado: " + p.getDescripcion());
            }
        }
        
        System.out.println("====== PROCESO DE IMÁGENES FINALIZADO ======");
    }
}
