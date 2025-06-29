package com.tallerwebi.dominio;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface ServicioReserva {
    void guardarReserva(Reserva reserva);
    List<Reserva> obtenerReservasPorEmail(String email); // ← Agregado
    void eliminarReserva (String email, String fechaIda, String fechaVuelta);
    void editarReserva(Long idVuelo, String email, String origen, String destino, String fechaIda, String fechaVuelta);
    long contarReservasUltimosDias(String email, int dias);
    Reserva buscarPorIdYEmail(String email, Long id);
    void guardarReservaExcursion(Excursion excursion, Usuario usuario);
    Reserva buscarReservaPorExcursionYUsuario(Long idExcursion, Long idUsuario);

}
