package com.Tienda.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tienda.webapp.model.Proveedor;
import com.Tienda.webapp.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repo;

    public List<Proveedor> listarTodos() {
        return repo.findAll();
    }

    public Proveedor guardar(Proveedor proveedor) {
        if (proveedor.getEstadoProveedores() == null) {
            proveedor.setEstadoProveedores("A");
        }
        if (proveedor.getFecha() == null) {
            proveedor.setFecha(new Date());
        }
        return repo.save(proveedor);
    }

    public Proveedor buscarPorId(String id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(String id) {
        repo.deleteById(id);
    }
}
