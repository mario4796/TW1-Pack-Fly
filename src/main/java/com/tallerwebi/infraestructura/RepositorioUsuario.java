package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioUsuario {
    Usuario buscarUsuario(String email, String password);
    Usuario buscarUsuarioPorId(Long id);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
}