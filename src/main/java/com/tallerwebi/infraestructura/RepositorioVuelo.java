package com.tallerwebi.infraestructura;

import java.util.List;

import com.tallerwebi.dominio.entidades.Vuelo;

public interface RepositorioVuelo {
    void guardar(Vuelo vuelo);
    List<Vuelo> obtenerTodos();

}
