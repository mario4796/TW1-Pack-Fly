package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioPreferenciaUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPreferenciaUsuarioImpl implements ServicioPreferenciaUsuario {

    @Autowired
    private RepositorioPreferenciaUsuario repositorio;

    @Autowired
    private ServicioReserva servicioReserva;

    @Override
    public void registrarReservaVuelo(Usuario usuario, int cantidadAsientos, int millas) {
        PreferenciaUsuario p = getOcrear(usuario);
        p.setTipoViajeVuelo(cantidadAsientos == 1 ? "Solitario" : "Familiar");
        p.setMillasAcumuladas((p.getMillasAcumuladas() != null ? p.getMillasAcumuladas() : 0) + millas);

        long cantidad = servicioReserva.contarReservasUltimosDias(usuario.getEmail(), 14);
        p.setViajeroFrecuente(cantidad > 1);

        repositorio.actualizar(p);
    }

    @Override
    public void registrarReservaHotel(Usuario usuario, int cantidadPersonas) {
        PreferenciaUsuario p = getOcrear(usuario);
        p.setTipoHospedaje(cantidadPersonas == 1 ? "Solitario" : "Familiar");
        repositorio.actualizar(p);
    }

    @Override
    public void registrarReservaExcursion(Usuario usuario, String tipoExcursion) {
        PreferenciaUsuario p = getOcrear(usuario);
        p.setTipoExcursionPreferida(tipoExcursion);
        repositorio.actualizar(p);
    }

    private PreferenciaUsuario getOcrear(Usuario usuario) {
        PreferenciaUsuario p = repositorio.obtenerPorUsuario(usuario);
        if (p == null) {
            p = new PreferenciaUsuario();
            p.setUsuario(usuario);
            p.setMillasAcumuladas(0);
            repositorio.guardar(p);
        }
        return p;
    }

    @Override
    public PreferenciaUsuario obtenerPorUsuario(Usuario usuario) {
        // intenta cargarla
        PreferenciaUsuario p = repositorio.obtenerPorUsuario(usuario);
        if (p == null) {
            // si no existe, creamos una nueva
            p = new PreferenciaUsuario();
            p.setUsuario(usuario);
            p.setMillasAcumuladas(0);
            repositorio.guardar(p);
        }
        return p;
    }

}
