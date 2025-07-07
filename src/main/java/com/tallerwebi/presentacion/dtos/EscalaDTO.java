package com.tallerwebi.presentacion.dtos;

public class EscalaDTO {
    private int duration;
    private String name;
    private String id;
    private Boolean overnight;

    // getters y setters
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Boolean getOvernight() { return overnight; }
    public void setOvernight(Boolean overnight) { this.overnight = overnight; }
}
