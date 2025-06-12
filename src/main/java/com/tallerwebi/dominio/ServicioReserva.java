package com.tallerwebi.dominio;
import java.util.List;

public interface ServicioReserva {
    void guardarReserva(Reserva reserva);
    List<Reserva> obtenerReservasPorEmail(String email); // ← Agregado
}
