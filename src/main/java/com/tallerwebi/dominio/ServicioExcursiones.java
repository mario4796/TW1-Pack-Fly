// src/main/java/com/tallerwebi/dominio/ServicioExcursiones.java
package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioExcursiones {
    List<Excursion> getExcursiones(String location, String query);
}
