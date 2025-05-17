package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidadValijas;
    private boolean seguroViajero;
    private String tipoAsiento;
    private double precioFinal;

    @ManyToOne
    private Paquete paquete;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCantidadValijas() { return cantidadValijas; }
    public void setCantidadValijas(int cantidadValijas) { this.cantidadValijas = cantidadValijas; }

    public boolean isSeguroViajero() { return seguroViajero; }
    public void setSeguroViajero(boolean seguroViajero) { this.seguroViajero = seguroViajero; }

    public String getTipoAsiento() { return tipoAsiento; }
    public void setTipoAsiento(String tipoAsiento) { this.tipoAsiento = tipoAsiento; }

    public double getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(double precioFinal) { this.precioFinal = precioFinal; }

    public Paquete getPaquete() { return paquete; }
    public void setPaquete(Paquete paquete) { this.paquete = paquete; }
}
