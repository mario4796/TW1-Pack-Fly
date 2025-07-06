package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Reserva;
import java.util.List;

public interface RepositorioReserva {
    void guardar(Reserva reserva);
    List<Reserva> buscarPorEmail(String email); // ← Agregado
    void eliminarReserva(String email, String fechaIda, String fechaVuelta);
    Reserva buscarPorIdyEmail(String email, Long idVuelo);
    void actualizar(Reserva reserva);
    Reserva buscarPorId(Long id);
    void pagarReservasDeVuelo(String email);
    List<Reserva> buscarPorEmailPagadas (String email);
}
