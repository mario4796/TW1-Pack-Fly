package com.tallerwebi.dominio.entidades;

import javax.persistence.Embeddable;

@Embeddable
public class Aeropuerto {
    private String id;
    private String name;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}