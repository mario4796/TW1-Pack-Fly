package com.tallerwebi.dominio.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.ManyToOne;


import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    // Datos del vuelo
    private String origen;
    private String destino;
    private String fechaIda;
    private String fechaVuelta;
    @JsonProperty("price")
    private Double precio;
    private Boolean pagado;
    @JsonProperty("total_duration")
    private String duracionTotal;

    @Transient
    private List<SegmentoVuelo> segmentos;

    @ManyToOne
    private Usuario usuario;

    // =========================
    // Constructores
    // =========================

    public Vuelo() {
        this.pagado = false;
    }

    public Vuelo(String nombre, String email, String origen, String destino, String fechaIda, String fechaVuelta, Double precio) {
        this.nombre = nombre;
        this.email = email;
        this.origen = origen;
        this.destino = destino;
        this.fechaIda = fechaIda;
        this.fechaVuelta = fechaVuelta;
        this.precio = precio;
        this.pagado = false;
    }

    // =========================
    // Getters y Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFechaIda() {
        return fechaIda;
    }

    public void setFechaIda(String fechaIda) {
        this.fechaIda = fechaIda;
    }

    public String getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(String fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDuracionTotal() {
        return duracionTotal;
    }

    public void setDuracionTotal(String duracionTotal) {
        this.duracionTotal = duracionTotal;
    }

    public List<SegmentoVuelo> getSegmentos() {
        return segmentos;
    }

    @JsonSetter("flights")
    public void setSegmentos(List<SegmentoVuelo> segmentos) {
        this.segmentos = segmentos;
        derivarOrigenYDestino();
        derivarFechas();
    }

    // =========================
    // Lógica derivada desde segmentos
    // =========================

    private void derivarOrigenYDestino() {
        if (segmentos != null && !segmentos.isEmpty()) {
            this.origen = segmentos.get(0).getAeropuertoSalida().getNombre();
            this.destino = segmentos.get(0).getAeropuertoLlegada().getNombre();
        }
    }

    private void derivarFechas() {
        if (segmentos != null && !segmentos.isEmpty()) {
            this.fechaIda = segmentos.get(0).getAeropuertoSalida().getFecha();
            this.fechaVuelta = segmentos.get(0).getAeropuertoLlegada().getFecha();
        }
    }

    // =========================
    // Clases internas: SegmentoVuelo y Aeropuerto
    // =========================

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SegmentoVuelo {

        @JsonProperty("departure_airport")
        private Aeropuerto aeropuertoSalida;

        @JsonProperty("arrival_airport")
        private Aeropuerto aeropuertoLlegada;

        public Aeropuerto getAeropuertoSalida() {
            return aeropuertoSalida;
        }

        public void setAeropuertoSalida(Aeropuerto aeropuertoSalida) {
            this.aeropuertoSalida = aeropuertoSalida;
        }

        public Aeropuerto getAeropuertoLlegada() {
            return aeropuertoLlegada;
        }

        public void setAeropuertoLlegada(Aeropuerto aeropuertoLlegada) {
            this.aeropuertoLlegada = aeropuertoLlegada;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Aeropuerto {

        @JsonProperty("name")
        private String nombre;

        @JsonProperty("id")
        private String codigoIATA;

        @JsonProperty("time")
        private String fecha;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCodigoIATA() {
            return codigoIATA;
        }

        public void setCodigoIATA(String codigoIATA) {
            this.codigoIATA = codigoIATA;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }
}