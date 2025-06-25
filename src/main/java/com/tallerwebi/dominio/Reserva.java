package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;

import javax.persistence.ManyToOne;


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
    private String fechaIda;
    private String fechaVuelta;
    private Double precio;
    @ManyToOne
    private Usuario usuario;


    public Reserva() {
    }

    public Reserva(String nombre, String email, String origen, String destino, String fechaIda, String fechaVuelta, Double precio) {
        this.nombre = nombre;
        this.email = email;
        this.origen = origen;
        this.destino = destino;
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.precio = precio;
    }



    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getFechaIda() {
        return fechaIda;
    }

    public String getFechaVuelta() {
        return fechaVuelta;
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

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setFechaIda(String fechaIda) {
        this.fechaIda = fechaIda;
    }

    public void setFechaVuelta(String fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
