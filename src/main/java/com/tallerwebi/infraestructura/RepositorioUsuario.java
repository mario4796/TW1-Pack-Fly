package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.PreferenciaViaje;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.PreferenciaViajeDTO;

public interface RepositorioUsuario {
    Usuario buscarUsuario(String email, String password);
    Usuario buscarUsuarioPorId(Long id);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    void guardarPreferenciaViaje(PreferenciaViaje preferencias);

    PreferenciaViaje obtenerPreferenciaViaje(Long usuarioId);
}