package com.tallerwebi.dominio;
import java.util.List;

public interface RepositorioReserva {
    void guardar(Reserva reserva);
    List<Reserva> buscarPorEmail(String email); // ← Agregado
}
