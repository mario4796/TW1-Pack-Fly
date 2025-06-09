package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vuelo {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("price")
    private int precio;
    @JsonProperty("total_duration")
    private String duracionTotal;

    @JsonProperty("flights")
    private List<SegmentoVuelo> segmentos;

    private String origen;
    private String destino;

    private String fechaIda;
    private String fechaVuelta;


    // Getters y Setters


    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
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

    public void setSegmentos(List<SegmentoVuelo> segmentos) {
        this.segmentos = segmentos;
        derivarOrigenYDestino(); // actualizar origen y destino cuando se setea
        devolverFechas();
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

    // Método para derivar origen y destino desde los segmentos
    public void derivarOrigenYDestino() {
        if (segmentos != null && !segmentos.isEmpty()) {
            this.origen = segmentos.get(0).getAeropuertoSalida().getNombre();
            this.destino = segmentos.get(0).getAeropuertoLlegada().getNombre();
        }
    }

    public void devolverFechas() {
        if (segmentos != null && !segmentos.isEmpty()) {
            this.fechaIda = segmentos.get(0).getAeropuertoSalida().getFecha();
            this.fechaVuelta = segmentos.get(0).getAeropuertoLlegada().getFecha();
        }
    }

    // Clase anidada para el precio
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Precio {
        @JsonProperty("amount")
        private String valor;

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    // Clase anidada para cada segmento de vuelo
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

    // Clase anidada para aeropuerto
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

    }
}
