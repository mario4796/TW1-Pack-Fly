package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Excursion;
import java.util.List;

public interface RepositorioExcursion {

    void guardar(Excursion excursion);
    List<Excursion> obtenerTodas();
    List<Excursion> obtenerPorUsuario(Long idUsuario);
    void eliminarReserva(Long idUsuario, String title);
    Excursion buscarPorUsuarioYExcursion(Long idUsuario, Long idExcursion);
    void actualizar(Excursion excursion);
    Excursion buscarPorId(Long id);
    void pagarExcursiones(Long id);
    List<Excursion> obtenerPorUsuarioPagado(Long id);
}
