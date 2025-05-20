package com.tallerwebi.dominio;
import com.tallerwebi.dominio.Vuelo;
import com.tallerwebi.dominio.VueloImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VueloService {

    private List<Vuelo> vuelos = new ArrayList<>();

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    public List<Vuelo> obtenerTodos() {
        return vuelos;
    }

    public Vuelo obtenerPorIndice(int indice) {
        return vuelos.get(indice);
    }
}