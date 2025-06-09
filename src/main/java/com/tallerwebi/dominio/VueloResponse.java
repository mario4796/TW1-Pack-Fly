package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VueloResponse {

    @JsonProperty("best_flights")
    private List<Vuelo> mejoresVuelos;

    @JsonProperty("other_flights")
    private List<Vuelo> otrosVuelos;

    public List<Vuelo> getMejoresVuelos() {
        return mejoresVuelos;
    }

    public void setMejoresVuelos(List<Vuelo> mejoresVuelos) {
        this.mejoresVuelos = mejoresVuelos;
    }

    public List<Vuelo> getOtrosVuelos() {
        return otrosVuelos;
    }

    public void setOtrosVuelos(List<Vuelo> otrosVuelos) {
        this.otrosVuelos = otrosVuelos;
    }
}

