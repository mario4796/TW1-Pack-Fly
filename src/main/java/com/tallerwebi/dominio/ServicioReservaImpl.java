package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

    private RepositorioReserva repositorioReserva;

    @Autowired
    public ServicioReservaImpl(RepositorioReserva repositorioReserva) {
        this.repositorioReserva = repositorioReserva;
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        repositorioReserva.guardar(reserva);
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
    public void editarReserva(Long idVuelo, String email, String origen, String destino, String fechaIda, String fechaVuelta) {
        Reserva reserva = repositorioReserva.buscarPorIdyEmail(email, idVuelo);
        if (reserva != null) {
            reserva.setOrigen(origen);
            reserva.setDestino(destino);
            reserva.setFechaIda(fechaIda);
            reserva.setFechaVuelta(fechaVuelta);
            repositorioReserva.actualizar(reserva);
        }
    }


}
