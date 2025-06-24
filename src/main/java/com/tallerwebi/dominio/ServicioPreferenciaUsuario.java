package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioPreferenciaUsuario {
    void registrarReservaVuelo(Usuario usuario, int cantidadAsientos, int millas);
    void registrarReservaHotel(Usuario usuario, int cantidadPersonas);
    void registrarReservaExcursion(Usuario usuario, String tipoExcursion);
}
