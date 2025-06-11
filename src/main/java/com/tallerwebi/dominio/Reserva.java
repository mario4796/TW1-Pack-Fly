package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    // Datos del vuelo
    private String origen;
    private String destino;
    private String fechaSalida;
    private String horaSalida;
    private String aerolinea;

    public Reserva() {
    }

    public Reserva(String nombre, String email, String origen, String destino, String fechaSalida, String horaSalida, String aerolinea) {
        this.nombre = nombre;
        this.email = email;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.aerolinea = aerolinea;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    // Getters y setters omitidos por brevedad, pero deben agregarse todos.
}
