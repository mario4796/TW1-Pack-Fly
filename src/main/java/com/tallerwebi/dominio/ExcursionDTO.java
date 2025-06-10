// src/main/java/com/tallerwebi/dominio/ExcursionImpl.java
package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.ThreadLocalRandom;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcursionDTO {
    @JsonProperty("title")
    private String title;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("location")
    private String location;

    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private String url;

    @JsonProperty("price")
    private Double precio;

    public ExcursionDTO(Excursion excursion) {
        this.title = excursion.getTitle();
        this.startDate = excursion.getStartDate();
        this.location = excursion.getLocation();
        this.description = excursion.getDescription();
        this.url = excursion.getUrl();

        this.precio = ThreadLocalRandom.current().nextDouble(1000.0, 5000.0);
    }


    public Excursion toEntity(){
        Excursion entidad = new Excursion();
        entidad.setTitle(this.title);
        entidad.setStartDate(this.startDate);
        entidad.setLocation(this.location);
        entidad.setDescription(this.description);
        entidad.setUrl(this.url);
        entidad.setPrecio(this.precio);
        return entidad;
    }


    public String getTitle()       { return title; }
    public String getStartDate()   { return startDate; }
    public String getLocation()    { return location; }
    public String getDescription() { return description; }
    public String getUrl()         { return url; }
    public Double getPrecio() {return precio; }

    public void setTitle(String t)           { this.title = t; }
    public void setStartDate(String d)       { this.startDate = d; }
    public void setLocation(String l)        { this.location = l; }
    public void setDescription(String d)     { this.description = d; }
    public void setUrl(String u)             { this.url = u; }
    public void setPrecio(Double p) {this.precio = p; }
}
