package com.tallerwebi.dominio;

import java.util.List;
import java.util.List;
import com.tallerwebi.dominio.Vuelo;

public interface RepositorioVuelo {
    void guardar(Vuelo vuelo);
    List<Vuelo> obtenerTodos();

}
