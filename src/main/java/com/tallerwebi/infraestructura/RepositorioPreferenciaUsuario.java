package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioPreferenciaUsuario {
    PreferenciaUsuario obtenerPorUsuario(Usuario usuario);
    void guardar(PreferenciaUsuario preferenciaUsuario);
    void actualizar(PreferenciaUsuario preferenciaUsuario);
}
