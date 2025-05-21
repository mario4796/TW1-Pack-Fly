package com.tallerwebi.dominio;

import java.time.LocalDate;

public class HotelResponse {
    private String location;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adults;

    public HotelResponse() {
    }

    public HotelResponse(String location, LocalDate checkInDate, LocalDate checkOutDate, int adults) {
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adults = adults;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }
}
