package com.tallerwebi.presentacion.dtos;

import java.util.List;

public class SegmentoVueloDTO {
    private AeropuertoDTO departureAirport;
    private AeropuertoDTO arrivalAirport;
    private int duration;
    private String airplane;
    private String airline;
    private String airlineLogo;
    private String travelClass;
    private String flightNumber;
    private List<String> extensions;
    private List<String> ticketAlsoSoldBy;
    private String legroom;
    private Boolean overnight;
    private Boolean oftenDelayedByOver30Min;
    private String planeAndCrewBy;


    public AeropuertoDTO getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AeropuertoDTO departureAirport) {
        this.departureAirport = departureAirport;
    }

    public AeropuertoDTO getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AeropuertoDTO arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(String airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }

    public List<String> getTicketAlsoSoldBy() {
        return ticketAlsoSoldBy;
    }

    public void setTicketAlsoSoldBy(List<String> ticketAlsoSoldBy) {
        this.ticketAlsoSoldBy = ticketAlsoSoldBy;
    }

    public String getLegroom() {
        return legroom;
    }

    public void setLegroom(String legroom) {
        this.legroom = legroom;
    }

    public Boolean getOvernight() {
        return overnight;
    }

    public void setOvernight(Boolean overnight) {
        this.overnight = overnight;
    }

    public Boolean getOftenDelayedByOver30Min() {
        return oftenDelayedByOver30Min;
    }

    public void setOftenDelayedByOver30Min(Boolean oftenDelayedByOver30Min) {
        this.oftenDelayedByOver30Min = oftenDelayedByOver30Min;
    }

    public String getPlaneAndCrewBy() {
        return planeAndCrewBy;
    }

    public void setPlaneAndCrewBy(String planeAndCrewBy) {
        this.planeAndCrewBy = planeAndCrewBy;
    }
}
