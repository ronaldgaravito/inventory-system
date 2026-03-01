package com.Tienda.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tienda.webapp.model.RepLegal;
import com.Tienda.webapp.repository.RepLegalRepository;

@Service
public class RepLegalService {

    @Autowired
    private RepLegalRepository repo;

    public List<RepLegal> listarTodos() {
        return repo.findAll();
    }

    public RepLegal guardar(RepLegal rep) {
        if (rep.getEstadoRepLegal() == null) {
            rep.setEstadoRepLegal("A");
        }
        if (rep.getFecha() == null) {
            rep.setFecha(new Date());
        }
        return repo.save(rep);
    }

    public RepLegal buscarPorId(String id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(String id) {
        repo.deleteById(id);
    }
}
