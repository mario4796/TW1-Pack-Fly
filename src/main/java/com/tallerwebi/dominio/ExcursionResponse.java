// src/main/java/com/tallerwebi/dominio/ExcursionResponse.java
package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcursionResponse {
    @JsonProperty("events")
    private List<ExcursionImpl> events;

    public List<ExcursionImpl> getEvents()      { return events; }
    public void setEvents(List<ExcursionImpl> e){ this.events = e; }
}
