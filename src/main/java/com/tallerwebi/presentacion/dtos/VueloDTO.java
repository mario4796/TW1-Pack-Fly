package com.tallerwebi.presentacion.dtos;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.Vuelo;

public class VueloDTO {
    private String origen;
    private String destino;
    private String fechaIda;
    private String fechaVuelta;
    private double precio;

    public VueloDTO() {}

    public VueloDTO(String origen, String destino, String fechaIda, String fechaVuelta, double precio) {
        this.origen = origen;
        this.destino = destino;
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.precio = precio;
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getFechaIda() { return fechaIda; }
    public void setFechaIda(String fechaIda) { this.fechaIda = fechaIda; }

    public String getFechaVuelta() { return fechaVuelta; }
    public void setFechaVuelta(String fechaVuelta) { this.fechaVuelta = fechaVuelta; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Vuelo toEntidad(String nombre,  String emailUsuario, Usuario usuario) {
        Vuelo vuelo = new Vuelo();
        vuelo.setOrigen(this.origen);
        vuelo.setDestino(this.destino);
        vuelo.setFechaIda(this.fechaIda);         // ya es String
        vuelo.setFechaVuelta(this.fechaVuelta);   // ya es String
        vuelo.setPrecio(this.precio);
        vuelo.setNombre(nombre);
        vuelo.setEmail(emailUsuario);
        vuelo.setUsuario(usuario);
        vuelo.setPagado(false);
        return vuelo;
    }
}
