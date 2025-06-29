package com.tallerwebi.presentacion.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcursionResponse {
    @JsonProperty("events")
    private List<ExcursionDTO> events;

    public List<ExcursionDTO> getEvents()      { return events; }
    public void setEvents(List<ExcursionDTO> e){ this.events = e; }
}
