package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva_pagada")
public class ReservaPagada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Vuelo vuelo;

    @OneToOne(optional = true)
    @JoinColumn(name = "hotel_id", nullable = true)
    private Hotel hotel;

    @OneToOne(optional = true)
    @JoinColumn(name = "excursion_id", nullable = true)
    private Excursion excursion;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    public ReservaPagada() { }

    public ReservaPagada(Vuelo vuelo, Hotel hotel, Excursion excursion, LocalDateTime fechaPago) {
        this.vuelo = vuelo;
        this.hotel = hotel;
        this.excursion = excursion;
        this.fechaPago = fechaPago;
    }

    public static ReservaPagada ofSoloVuelo(Vuelo vuelo, LocalDateTime fechaPago) {
        return new ReservaPagada(vuelo, null, null, fechaPago);
    }

    public static ReservaPagada ofSoloHotel(Vuelo vuelo, Hotel hotel, LocalDateTime fechaPago) {
        return new ReservaPagada(vuelo, hotel, null, fechaPago);
    }

    public static ReservaPagada ofSoloExcursion(Vuelo vuelo, Excursion excursion, LocalDateTime fechaPago) {
        return new ReservaPagada(vuelo, null, excursion, fechaPago);
    }

    public static ReservaPagada ofVueloYHotel(Vuelo vuelo, Hotel hotel, LocalDateTime fechaPago) {
        return new ReservaPagada(vuelo, hotel, null, fechaPago);
    }

    public static ReservaPagada ofVueloYExcursion(Vuelo vuelo, Excursion excursion, LocalDateTime fechaPago) {
        return new ReservaPagada(vuelo, null, excursion, fechaPago);
    }

    public static ReservaPagada ofHotelYExcursion(Vuelo vuelo, Hotel hotel, Excursion excursion, LocalDateTime fechaPago) {
        return new ReservaPagada(vuelo, hotel, excursion, fechaPago);
    }

    public Long getId() {
        return id;
    }

    public Vuelo getReserva() {
        return vuelo;
    }

    public void setReserva(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
}