package com.tallerwebi.presentacion.dtos;

import com.tallerwebi.dominio.entidades.PreferenciaViaje;
import com.tallerwebi.dominio.entidades.Usuario;

public class PreferenciaViajeDTO {
    private Long id;
    // Vuelos
    private String departure_id;
    private Integer travel_class;
    private String outbound_times;
    private Boolean permitir_escalas;
    private Integer sort_vuelos;
    private Double vuelo_budget;
    // Hoteles
    private Double hotel_budget;
    private Integer hotel_stars;
    private Integer hotel_rating;
    private Integer sort_hoteles;
    // Excursiones
    private Double excursion_budget;
    // General
    private String currency;

    private Usuario usuario;

    public PreferenciaViajeDTO() {}

    public PreferenciaViajeDTO(PreferenciaViaje entidad) {
        this.id = entidad.getId();
        this.departure_id = entidad.getDeparture_id();
        this.travel_class = entidad.getTravel_class();
        this.outbound_times = entidad.getOutbound_times();
        this.permitir_escalas = entidad.getPermitir_escalas();
        this.sort_vuelos = entidad.getSort_vuelos();
        this.vuelo_budget = entidad.getVuelo_budget();
        this.hotel_budget = entidad.getHotel_budget();
        this.hotel_stars = entidad.getHotel_stars();
        this.hotel_rating = entidad.getHotel_rating();
        this.sort_hoteles = entidad.getSort_hoteles();
        this.excursion_budget = entidad.getExcursion_budget();
        this.currency = entidad.getCurrency();
        this.usuario = entidad.getUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeparture_id() {
        return departure_id;
    }

    public void setDeparture_id(String departure_id) {
        this.departure_id = departure_id;
    }

    public Integer getTravel_class() {
        return travel_class;
    }

    public void setTravel_class(Integer travel_class) {
        this.travel_class = travel_class;
    }

    public String getOutbound_times() {
        return outbound_times;
    }

    public void setOutbound_times(String outbound_times) {
        this.outbound_times = outbound_times;
    }

    public Boolean getPermitir_escalas() {
        return permitir_escalas;
    }

    public void setPermitir_escalas(Boolean permitir_escalas) {
        this.permitir_escalas = permitir_escalas;
    }

    public Integer getSort_vuelos() {
        return sort_vuelos;
    }

    public void setSort_vuelos(Integer sort_vuelos) {
        this.sort_vuelos = sort_vuelos;
    }

    public Double getVuelo_budget() {
        return vuelo_budget;
    }

    public void setVuelo_budget(Double vuelo_budget) {
        this.vuelo_budget = vuelo_budget;
    }

    public Double getHotel_budget() {
        return hotel_budget;
    }

    public void setHotel_budget(Double hotel_budget) {
        this.hotel_budget = hotel_budget;
    }

    public Integer getHotel_stars() {
        return hotel_stars;
    }

    public void setHotel_stars(Integer hotel_stars) {
        this.hotel_stars = hotel_stars;
    }

    public Integer getHotel_rating() {
        return hotel_rating;
    }

    public void setHotel_rating(Integer hotel_rating) {
        this.hotel_rating = hotel_rating;
    }

    public Integer getSort_hoteles() {
        return sort_hoteles;
    }

    public void setSort_hoteles(Integer sort_hoteles) {
        this.sort_hoteles = sort_hoteles;
    }

    public Double getExcursion_budget() {
        return excursion_budget;
    }

    public void setExcursion_budget(Double excursion_budget) {
        this.excursion_budget = excursion_budget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
