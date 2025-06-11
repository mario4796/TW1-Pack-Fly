package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
