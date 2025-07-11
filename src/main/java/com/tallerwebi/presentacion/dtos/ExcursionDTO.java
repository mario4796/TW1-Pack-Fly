package com.tallerwebi.presentacion.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Usuario;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcursionDTO {
    @JsonProperty("title")
    private String title;

    private String startDate;
    private Boolean pagado;

    @JsonProperty("name")
    private String location;

    @JsonProperty("description")
    private String description;

    private String thumbnail;

    @JsonProperty("link")
    private String url;

    private Double precio;

    @JsonProperty("date")
    private void unpackDate(Map<String, Object> date) {
        this.startDate = (String) date.get("start_date");
    }
    @JsonProperty("venue")
    private void unpackvenue(Map<String, Object> venue) {
        this.location = (String) venue.get("name");
    }

    private Usuario usuario;

    public ExcursionDTO() {
        // Constructor vacío requerido por Jackson
    }

    public ExcursionDTO(Excursion excursion) {
        this.title = excursion.getTitle();
        this.startDate = excursion.getStartDate();
        this.location = excursion.getLocation();
        this.description = excursion.getDescription();
        this.url = excursion.getUrl();
        this.thumbnail = excursion.getThumbnail();
        this.pagado= excursion.getPagado();

    }
    public ExcursionDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Excursion toEntity(){

        System.out.println("DTO URL: " + this.url);
        System.out.println("DTO THUMBNAIL: " + this.thumbnail);

        Excursion entidad = new Excursion();
        entidad.setTitle(this.title);
        entidad.setStartDate(this.startDate);
        entidad.setLocation(this.location);
        entidad.setDescription(this.description);
        entidad.setUrl(this.url);
        entidad.setPrecio(this.precio);
        entidad.setPagado(false);
        entidad.setThumbnail(this.thumbnail);
        entidad.setUsuario(usuario);

        return entidad;
    }

    public String getTitle()       { return title; }
    public String getStartDate()   { return startDate; }
    public String getLocation()    { return location; }
    public String getDescription() { return description; }
    public String getUrl()         { return url; }
    public Double getPrecio() {return precio; }
    public String getThumbnail()       { return thumbnail; } // ✅ getter
    public void setTitle(String t)           { this.title = t; }
    public void setStartDate(String d)       { this.startDate = d; }
    public void setLocation(String l)        { this.location = l; }
    public void setDescription(String d)     { this.description = d; }
    public void setUrl(String u)             { this.url = u; }
    public void setPrecio(Double p) {this.precio = p; }
    public void setThumbnail(String t) { this.thumbnail = t; }
}