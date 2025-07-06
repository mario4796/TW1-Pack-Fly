package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.entidades.Vuelo;

import java.util.List;

public interface RepositorioReserva {
    void guardar(Vuelo vuelo);
    List<Vuelo> buscarPorEmail(String email); // ← Agregado

    List<Vuelo> obtenerTodos();

    void eliminarReserva(String email, String fechaIda, String fechaVuelta);
    Vuelo buscarPorIdyEmail(String email, Long idVuelo);
    void actualizar(Vuelo vuelo);
    Vuelo buscarPorId(Long id);
    void pagarReservasDeVuelo(String email);
    List<Vuelo> buscarPorEmailPagadas (String email);

}
