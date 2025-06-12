// src/main/java/com/tallerwebi/dominio/ServicioExcursiones.java
package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dtos.ExcursionDTO;

import java.util.List;

public interface ServicioExcursiones {
    List<ExcursionDTO> getExcursiones(String location, String query);
    void guardarExcursion(Excursion excursion);
    List<Excursion> obtenerExcursionesDeUsuario(Long idUsuario);
}
