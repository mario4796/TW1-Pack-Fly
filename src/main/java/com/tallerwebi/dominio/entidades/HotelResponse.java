package com.tallerwebi.dominio.entidades;

import com.tallerwebi.presentacion.dtos.HotelDto;

import java.util.List;

public class HotelResponse {
    private List<HotelDto> properties;

    public List<HotelDto> getProperties() {
        return properties;
    }

    public void setProperties(List<HotelDto> properties) {
        this.properties = properties;
    }
}