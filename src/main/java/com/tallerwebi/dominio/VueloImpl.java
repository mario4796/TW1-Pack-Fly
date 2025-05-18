package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VueloImpl implements Vuelo {
    private String fechaIda;
    private String fechaVuelta;
    private String origen;
    private String destino;
    private double precioBase;

    // Extras opcionales
    private double costoValija = 0;
    private double costoSeguro = 0;
    private double costoAsiento = 0;

    public VueloImpl(String fechaIda, String fechaVuelta, String origen, String destino, double precioBase) {
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.origen = origen;
        this.destino = destino;
        this.precioBase = precioBase;
    }

    @Override
    public String getFechaIda() {
        return fechaIda;
    }

    @Override
    public String getFechaVuelta() {
        return fechaVuelta;
    }

    @Override
    public String getOrigen() {
        return origen;
    }

    @Override
    public String getDestino() {
        return destino;
    }

    @Override
    public void agregarValija(double costo) {
        this.costoValija = costo;
    }

    @Override
    public void agregarSeguro(double costo) {
        this.costoSeguro = costo;
    }

    @Override
    public void seleccionarAsiento(double costo) {
        this.costoAsiento = costo;
    }

    @Override
    public double calcularPrecioFinal() {
        return precioBase + costoValija + costoSeguro + costoAsiento;
    }
}