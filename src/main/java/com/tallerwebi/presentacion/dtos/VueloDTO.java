package com.tallerwebi.presentacion.dtos;

import com.tallerwebi.dominio.entidades.*;

import java.util.ArrayList;
import java.util.List;

public class VueloDTO {
    private List<SegmentoVueloDTO> flights;
    private List<EscalaDTO> layovers;
    private String origen;
    private String destino;
    private String fechaIda;
    private String fechaVuelta;
    private double precio;
    private Integer duracionTotal;

    public VueloDTO() {}

    public VueloDTO(String origen, String destino, String fechaIda, String fechaVuelta, double precio) {
        this.origen = origen;
        this.destino = destino;
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.precio = precio;
        this.flights = new ArrayList<>();
        this.layovers = new ArrayList<>();
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getFechaIda() { return fechaIda; }
    public void setFechaIda(String fechaIda) { this.fechaIda = fechaIda; }

    public String getFechaVuelta() { return fechaVuelta; }
    public void setFechaVuelta(String fechaVuelta) { this.fechaVuelta = fechaVuelta; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public List<SegmentoVueloDTO> getFlights() {
        return flights;
    }

    public void setFlights(List<SegmentoVueloDTO> flights) {
        this.flights = flights;
    }

    public List<EscalaDTO> getLayovers() {
        return layovers;
    }

    public void setLayovers(List<EscalaDTO> layovers) {
        this.layovers = layovers;
    }

    public Integer getDuracionTotal() {
        return duracionTotal;
    }

    public void setDuracionTotal(Integer duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public Vuelo toEntidad(String nombre, String emailUsuario, Usuario usuario) {
        Vuelo vuelo = new Vuelo();
        vuelo.setOrigen(this.origen);
        vuelo.setDestino(this.destino);
        vuelo.setFechaIda(this.fechaIda);         // ya es String
        vuelo.setFechaVuelta(this.fechaVuelta);   // ya es String
        vuelo.setPrecio(this.precio);
        vuelo.setNombre(nombre);
        vuelo.setEmail(emailUsuario);
        vuelo.setUsuario(usuario);
        vuelo.setPagado(false);

        // MAPEAR SEGMENTOS DE VUELO
        List<SegmentoVuelo> segmentos = new ArrayList<>();
        if (this.flights != null) {
            for (SegmentoVueloDTO segDto : this.flights) {
                SegmentoVuelo seg = new SegmentoVuelo();
                seg.setVuelo(vuelo); // relación inversa

                Aeropuerto dep = new Aeropuerto();
                dep.setId(segDto.getDepartureAirport().getId());
                dep.setName(segDto.getDepartureAirport().getName());
                dep.setTime(segDto.getDepartureAirport().getTime());

                Aeropuerto arr = new Aeropuerto();
                arr.setId(segDto.getArrivalAirport().getId());
                arr.setName(segDto.getArrivalAirport().getName());
                arr.setTime(segDto.getArrivalAirport().getTime());

                seg.setDepartureAirport(dep);
                seg.setArrivalAirport(arr);
                seg.setDuration(segDto.getDuration());
                seg.setAirplane(segDto.getAirplane());
                seg.setAirline(segDto.getAirline());
                seg.setTravelClass(segDto.getTravelClass());
                seg.setFlightNumber(segDto.getFlightNumber());
                seg.setLegroom(segDto.getLegroom());
                seg.setOvernight(segDto.getOvernight());
                seg.setOftenDelayedByOver30Min(segDto.getOftenDelayedByOver30Min());
                seg.setPlaneAndCrewBy(segDto.getPlaneAndCrewBy());
                seg.setExtensions(segDto.getExtensions());
                seg.setTicketAlsoSoldBy(segDto.getTicketAlsoSoldBy());

                segmentos.add(seg);
            }
        }
        vuelo.setFlights(segmentos);

// MAPEAR ESCALAS
        List<Escala> escalas = new ArrayList<>();
        if (this.layovers != null) {
            for (EscalaDTO escDto : this.layovers) {
                Escala escala = new Escala();
                escala.setVuelo(vuelo);
                escala.setDuration(escDto.getDuration());
                escala.setName(escDto.getName());
                escala.setOvernight(escDto.getOvernight());
                escalas.add(escala);
            }
        }
        vuelo.setLayovers(escalas);

        return vuelo;
    }

}
