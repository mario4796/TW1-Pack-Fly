package com.tallerwebi.dominio;
import com.tallerwebi.dominio.entidades.Reserva;

import java.util.List;

public interface ServicioReserva {
    void guardarReserva(Reserva reserva);
    List<Reserva> obtenerReservasPorEmail(String email); // ← Agregado
    void eliminarReserva (String email, String fechaIda, String fechaVuelta);
    void editarReserva(Long idVuelo, String email, String origen, String destino, String fechaIda, String fechaVuelta);
    long contarReservasUltimosDias(String email, int dias);

}
