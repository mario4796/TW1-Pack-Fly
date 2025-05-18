package com.tallerwebi.dominio;

public interface Vuelo {

    String getFechaIda();
    String getFechaVuelta();
    String getOrigen();
    String getDestino();
    double calcularPrecioFinal();

    void agregarValija(double costo);
    void agregarSeguro(double costo);
    void seleccionarAsiento(double costo);
}
