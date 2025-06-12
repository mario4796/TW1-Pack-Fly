package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("price")
    private Integer precio;

    @JsonProperty("total_duration")
    private String duracionTotal;

    @Transient // No lo persistimos en la base por ahora
    private List<SegmentoVuelo> segmentos;

    private String origen;
    private String destino;
    private String fechaIda;
    private String fechaVuelta;

    // Getters y setters básicos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
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

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getFechaIda() {
        return fechaIda;
    }

    public String getFechaVuelta() {
        return fechaVuelta;
    }

    // Derivar datos desde segmentos
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

    // =============================
    // Clases internas para el JSON
    // =============================

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
