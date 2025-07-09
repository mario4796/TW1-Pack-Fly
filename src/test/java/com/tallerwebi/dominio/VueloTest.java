package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.SegmentoVuelo;
import com.tallerwebi.dominio.entidades.Escala;
import com.tallerwebi.dominio.entidades.Aeropuerto;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VueloTest {

    @Test
    public void queElConstructorPorDefectoDejeCamposEnNull() {
        Vuelo vuelo = new Vuelo();

        assertNull(vuelo.getId());
        assertNull(vuelo.getPrecio());
        assertNull(vuelo.getDuracionTotal());
        assertNull(vuelo.getLayovers());
        assertNull(vuelo.getOrigen());
        assertNull(vuelo.getDestino());
        assertNull(vuelo.getFechaIda());
        assertNull(vuelo.getFechaVuelta());
        assertNull(vuelo.getFlights());
    }

    @Test
    public void queSePuedanSetearYObtenerCamposBasicos() {
        Vuelo vuelo = new Vuelo();
        vuelo.setId(1L);
        vuelo.setPrecio(200.0);
        vuelo.setDuracionTotal("5h20m");

        assertEquals(Long.valueOf(1L), vuelo.getId());
        assertEquals(Integer.valueOf(200), vuelo.getPrecio());
        assertEquals("5h20m", vuelo.getDuracionTotal());
    }

    @Test
    public void queSetearSegmentosActualiceOrigenDestinoYFechas() {
        // Aeropuerto de salida
        Aeropuerto salida = new Aeropuerto();
        salida.setName("Aeroparque");
        salida.setTime("2025-08-01");

        // Aeropuerto de llegada
        Aeropuerto llegada = new Aeropuerto();
        llegada.setName("El Prat");
        llegada.setTime("2025-08-15");

        SegmentoVuelo segmento = new SegmentoVuelo();
        segmento.setDepartureAirport(salida);
        segmento.setArrivalAirport(llegada);

        Vuelo vuelo = new Vuelo();
        vuelo.setFlights(Arrays.asList(segmento));

        assertEquals("Aeroparque", vuelo.getOrigen());
        assertEquals("El Prat", vuelo.getDestino());
        assertEquals("2025-08-01", vuelo.getFechaIda());
        assertEquals("2025-08-15", vuelo.getFechaVuelta());
    }
}
