package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Vuelo.Aeropuerto;
import com.tallerwebi.dominio.entidades.Vuelo.SegmentoVuelo;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VueloTest {

    @Test
    public void queDefaultConstructorDejeCamposEnNull() {
        Vuelo vuelo = new Vuelo();

        assertNull(vuelo.getId());
        assertNull(vuelo.getPrecio());
        assertNull(vuelo.getDuracionTotal());
        assertNull(vuelo.getSegmentos());
        assertNull(vuelo.getOrigen());
        assertNull(vuelo.getDestino());
        assertNull(vuelo.getFechaIda());
        assertNull(vuelo.getFechaVuelta());
    }

    @Test
    public void queSePuedaModificarYObtenerCamposBasicos() {
        Vuelo vuelo = new Vuelo();
        vuelo.setId(1L);
        vuelo.setPrecio(150);
        vuelo.setDuracionTotal("3h45m");

        assertEquals(Long.valueOf(1L), vuelo.getId());
        assertEquals(Integer.valueOf(150), vuelo.getPrecio());
        assertEquals("3h45m", vuelo.getDuracionTotal());
    }

    @Test
    public void queDeriveOrigenDestinoYFechasAlSetearSegmentos() {
        // preparar un segmento con aeropuertos de salida y llegada
        Aeropuerto salida = new Aeropuerto();
        salida.setNombre("Aeropuerto Ezeiza");
        salida.setFecha("2025-07-10");

        Aeropuerto llegada = new Aeropuerto();
        llegada.setNombre("Aeropuerto Madrid-Barajas");
        llegada.setFecha("2025-07-25");

        SegmentoVuelo segmento = new SegmentoVuelo();
        segmento.setAeropuertoSalida(salida);
        segmento.setAeropuertoLlegada(llegada);

        Vuelo vuelo = new Vuelo();
        List<SegmentoVuelo> segmentos = Arrays.asList(segmento);
        vuelo.setSegmentos(segmentos);

        assertEquals("Aeropuerto Ezeiza", vuelo.getOrigen());
        assertEquals("Aeropuerto Madrid-Barajas", vuelo.getDestino());
        assertEquals("2025-07-10", vuelo.getFechaIda());
        assertEquals("2025-07-25", vuelo.getFechaVuelta());
    }
}
