package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import java.util.List;

public interface ServicioExcursiones {
    List<ExcursionDTO> getExcursiones(String location, String query);
    void guardarExcursion(Excursion excursion);
    List<Excursion> obtenerExcursionesDeUsuario(Long idUsuario);
    void eliminarReserva(Long idUsuario, String title);
    void editarReserva(Long idExcursion, Long idUsuario, String title, String url);
    Excursion buscarPorId(Long id);
    void pagarExcursiones(Long id);
    List<Excursion> obtenerExcursionesDeUsuarioPagados(Long id);
}
