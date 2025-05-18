package com.tallerwebi.dominio;

import java.time.LocalDate;

public class Vuelo {
    private LocalDate fechaIda;
    private LocalDate fechaVuelta;
    private String aeropuerto;

    private Integer cantidadValijas;
    private boolean seguroViajero;
    private String asiento;

    private double precioBase = 100000;
    private double precioTotal;


    public Vuelo(LocalDate fechaIda, LocalDate fechaVuelta, String aeropuerto) {
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.aeropuerto = aeropuerto;
        recalcularPrecio();
    }

    public LocalDate getFechaIda() {
        return fechaIda;
    }

    public void setFechaIda(LocalDate fechaIda) {
        this.fechaIda = fechaIda;
    }

    public LocalDate getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(LocalDate fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public String getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(String aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public Integer getCantidadValijas() {
        return cantidadValijas;
    }

    public void setCantidadValijas(Integer cantidadValijas) {
        this.cantidadValijas = cantidadValijas;
        recalcularPrecio();
    }

    public boolean isSeguroViajero() {
        return seguroViajero;
    }

    public void setSeguroViajero(boolean seguroViajero) {
        this.seguroViajero = seguroViajero;
        recalcularPrecio();
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
        recalcularPrecio();
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // Metodo para recalcular precio total
    private void recalcularPrecio() {
        precioTotal = precioBase;

        if (cantidadValijas != null && cantidadValijas > 0) {
            precioTotal += cantidadValijas * 5000;
        }

        if (seguroViajero) {
            precioTotal += 10000;
        }

        if (asiento != null && !asiento.isEmpty()) {
            precioTotal += 3000;
        }
    }
}
