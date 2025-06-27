package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reserva reserva;

    private String numeroTarjeta;
    private String titular;
    private Double montoPagado;
    private LocalDateTime fechaPago;
    private Boolean pagoExitoso;


    public Long getId() { return id; }
    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public Double getMontoPagado() { return montoPagado; }
    public void setMontoPagado(Double montoPagado) { this.montoPagado = montoPagado; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
    public Boolean getPagoExitoso() { return pagoExitoso; }
    public void setPagoExitoso(Boolean pagoExitoso) { this.pagoExitoso = pagoExitoso; }
}