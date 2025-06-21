package com.tallerwebi.dominio;
import java.util.List;

public interface RepositorioReserva {
    void guardar(Reserva reserva);
    List<Reserva> buscarPorEmail(String email); // ← Agregado
    void eliminarReserva(String email, String fechaIda, String fechaVuelta);
    Reserva buscarPorIdyEmail(String email, Long idVuelo);
    void actualizar(Reserva reserva);
}
