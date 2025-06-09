package com.tallerwebi.dominio;

import java.util.List;

public class HotelResponse {
    private List<Hotel> properties;

    public List<Hotel> getProperties() {
        return properties;
    }

    public void setProperties(List<Hotel> properties) {
        this.properties = properties;
    }
}