package com.Tienda.webapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tienda.webapp.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByIdUsuarioAndPassword(String idUsuario, String password);

}
