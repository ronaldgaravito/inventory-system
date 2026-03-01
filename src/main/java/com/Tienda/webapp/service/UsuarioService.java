package com.Tienda.webapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tienda.webapp.model.Usuario;
import com.Tienda.webapp.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    public Usuario login(String user, String pass) {
        return repo.findByIdUsuarioAndPassword(user, pass).orElse(null);
    }

    public boolean existeUsuario(String username) {
        return repo.existsById(username);
    }

    public Usuario registrarUsuario(Usuario usuario) {
        // Establecer valores por defecto
        usuario.setIdRol(2); // Rol cliente por defecto
        usuario.setEstadoUsuario("Activo");
        usuario.setFechaCreacion(LocalDate.now());
        return repo.save(usuario);
    }
}
