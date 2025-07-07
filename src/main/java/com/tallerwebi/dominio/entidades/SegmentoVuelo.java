package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
public class SegmentoVuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "departure_id")),
            @AttributeOverride(name = "name", column = @Column(name = "departure_name")),
            @AttributeOverride(name = "time", column = @Column(name = "departure_time"))
    })
    private Aeropuerto departureAirport;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "arrival_id")),
            @AttributeOverride(name = "name", column = @Column(name = "arrival_name")),
            @AttributeOverride(name = "time", column = @Column(name = "arrival_time"))
    })
    private Aeropuerto arrivalAirport;

    private int duration;
    private String airplane;
    private String airline;
    private String travelClass;
    private String flightNumber;
    private String legroom;
    private Boolean overnight;
    private Boolean oftenDelayedByOver30Min;
    private String planeAndCrewBy;

    @ElementCollection
    private List<String> extensions;

    @ElementCollection
    private List<String> ticketAlsoSoldBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Aeropuerto getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Aeropuerto departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Aeropuerto getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Aeropuerto arrivalAirport) {
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
}
