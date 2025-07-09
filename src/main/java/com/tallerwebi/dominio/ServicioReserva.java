package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.presentacion.dtos.VueloDTO;
import java.util.Date;
import java.util.List;

public interface ServicioReserva {
      List<VueloDTO> getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta, String moneda, String tipoViaje);

      void guardarReserva(Vuelo vuelo);
    List<Vuelo> obtenerReservasPorEmail(String email); // ← Agregado
    void eliminarReserva (String email, String fechaIda, String fechaVuelta);
    void editarReserva(Long idVuelo, String email, String origen, String destino, String fechaIda, String fechaVuelta);
    long contarReservasUltimosDias(String email, int dias);
    Vuelo buscarPorId(Long id);
    void pagarRerservasDeVuelo (String email);
    List<Vuelo>  obtenerReservasPorEmailPagados(String email);
}
