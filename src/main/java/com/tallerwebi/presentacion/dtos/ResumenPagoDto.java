package com.tallerwebi.presentacion.dtos;

public class ResumenPagoDto {
    private double subtotal;
    private double impuestos;
    private double cargosServicio;
    private double descuentos;
    private double total;

    public ResumenPagoDto(double subtotal, double impuestos, double cargosServicio, double descuentos, double total) {
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.cargosServicio = cargosServicio;
        this.descuentos = descuentos;
        this.total = total;
    }

    public double getSubtotal() { return subtotal; }
    public double getImpuestos() { return impuestos; }
    public double getCargosServicio() { return cargosServicio; }
    public double getDescuentos() { return descuentos; }
    public double getTotal() { return total; }
}