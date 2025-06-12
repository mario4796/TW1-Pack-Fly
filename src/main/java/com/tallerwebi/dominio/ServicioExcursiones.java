// src/main/java/com/tallerwebi/dominio/ServicioExcursiones.java
package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioExcursiones {
    List<ExcursionDTO> getExcursiones(String location, String query);
    void guardarExcursion(Excursion excursion);
}
