package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.infraestructura.RepositorioReserva;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

    private final RepositorioReserva repositorioReserva;

    @Autowired
    private ServicioPreferenciaUsuario servicioPreferenciaUsuario;

    @Autowired
    public ServicioReservaImpl(RepositorioReserva repositorioReserva) {
        this.repositorioReserva = repositorioReserva;
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        repositorioReserva.guardar(reserva);


        int cantidadAsientos = 1;
        int millas = 100;

        Usuario usuario = reserva.getUsuario();
        if (usuario != null) {
            servicioPreferenciaUsuario.registrarReservaVuelo(usuario, cantidadAsientos, millas);
        } else {
            System.out.println("No se pudo registrar preferencias: la reserva no tiene asociado un usuario");
        }
    }

    @Override
    public List<Reserva> obtenerReservasPorEmail(String email) {
        return repositorioReserva.buscarPorEmail(email);
    }

    @Override
    public void eliminarReserva(String email, String fechaIda, String fechaVuelta) {
        repositorioReserva.eliminarReserva(email, fechaIda, fechaVuelta);
    }

    @Override
    public void editarReserva(Long idVuelo, String email, String origen, String destino,
                              String fechaIda, String fechaVuelta) {
        Reserva reserva = repositorioReserva.buscarPorIdyEmail(email, idVuelo);
        if (reserva != null) {
            reserva.setOrigen(origen);
            reserva.setDestino(destino);
            reserva.setFechaIda(fechaIda);
            reserva.setFechaVuelta(fechaVuelta);
            repositorioReserva.actualizar(reserva);
        }
    }

    @Override
    public long contarReservasUltimosDias(String email, int dias) {
        List<Reserva> reservas = repositorioReserva.buscarPorEmail(email);
        return reservas.stream()
                .filter(r -> {
                    try {
                        java.time.LocalDate fechaIda = java.time.LocalDate.parse(r.getFechaIda());
                        return fechaIda.isAfter(java.time.LocalDate.now().minusDays(dias));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();
    }
}
