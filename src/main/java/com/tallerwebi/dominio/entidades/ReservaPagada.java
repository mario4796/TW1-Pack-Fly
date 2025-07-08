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
    private Reserva reserva;

    @OneToOne(optional = true)
    @JoinColumn(name = "hotel_id", nullable = true)
    private Hotel hotel;

    @OneToOne(optional = true)
    @JoinColumn(name = "excursion_id", nullable = true)
    private Excursion excursion;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    public ReservaPagada() { }


    public ReservaPagada(Reserva reserva, Hotel hotel, Excursion excursion, LocalDateTime fechaPago) {
        this.reserva = reserva;
        this.hotel = hotel;
        this.excursion = excursion;
        this.fechaPago = fechaPago;
    }

    public static ReservaPagada ofSoloVuelo(Reserva reserva, LocalDateTime fechaPago) {
        return new ReservaPagada(reserva, null, null, fechaPago);
    }

    public static ReservaPagada ofSoloHotel(Reserva reserva, Hotel hotel, LocalDateTime fechaPago) {
        return new ReservaPagada(reserva, hotel, null, fechaPago);
    }

    public static ReservaPagada ofSoloExcursion(Reserva reserva, Excursion excursion, LocalDateTime fechaPago) {
        return new ReservaPagada(reserva, null, excursion, fechaPago);
    }

    public static ReservaPagada ofVueloYHotel(Reserva reserva, Hotel hotel, LocalDateTime fechaPago) {
        return new ReservaPagada(reserva, hotel, null, fechaPago);
    }

    public static ReservaPagada ofVueloYExcursion(Reserva reserva, Excursion excursion, LocalDateTime fechaPago) {
        return new ReservaPagada(reserva, null, excursion, fechaPago);
    }

    public static ReservaPagada ofHotelYExcursion(Reserva reserva, Hotel hotel, Excursion excursion, LocalDateTime fechaPago) {
        return new ReservaPagada(reserva, hotel, excursion, fechaPago);
    }


    public Long getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
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