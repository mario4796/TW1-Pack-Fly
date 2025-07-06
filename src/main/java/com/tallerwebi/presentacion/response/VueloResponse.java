package com.tallerwebi.presentacion.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.presentacion.dtos.VueloDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VueloResponse {

    @JsonProperty("best_flights")
    private List<VueloDTO> mejoresVuelos;

    @JsonProperty("other_flights")
    private List<VueloDTO> otrosVuelos;

    public List<VueloDTO> getMejoresVuelos() {
        return mejoresVuelos;
    }

    public void setMejoresVuelos(List<VueloDTO> mejoresVuelos) {
        this.mejoresVuelos = mejoresVuelos;
    }

    public List<VueloDTO> getOtrosVuelos() {
        return otrosVuelos;
    }

    public void setOtrosVuelos(List<VueloDTO> otrosVuelos) {
        this.otrosVuelos = otrosVuelos;
    }
}

