package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Vuelo;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReservaTest {

    @Test
    public void queSeCreeReservaConDatosCorrectos() {
        Vuelo vuelo = new Vuelo("Ana", "ana@email.com", "EZE", "MAD", "2025-07-10", "2025-07-25", 30.0);

        assertEquals("Ana", vuelo.getNombre());
        assertEquals("ana@email.com", vuelo.getEmail());
        assertEquals("EZE", vuelo.getOrigen());
        assertEquals("MAD", vuelo.getDestino());
        assertEquals("2025-07-10", vuelo.getFechaIda());
        assertEquals("2025-07-25", vuelo.getFechaVuelta());
        assertEquals(30.0, vuelo.getPrecio(), 0.001);
    }

    @Test
    public void queSePuedaModificarUnaReserva() {
        Vuelo vuelo = new Vuelo();
        vuelo.setNombre("Carlos");
        vuelo.setEmail("carlos@email.com");
        vuelo.setOrigen("BUE");
        vuelo.setDestino("ROM");
        vuelo.setFechaIda("2025-08-01");
        vuelo.setFechaVuelta("2025-08-15");

        assertEquals("Carlos", vuelo.getNombre());
        assertEquals("carlos@email.com", vuelo.getEmail());
        assertEquals("BUE", vuelo.getOrigen());
        assertEquals("ROM", vuelo.getDestino());
        assertEquals("2025-08-01", vuelo.getFechaIda());
        assertEquals("2025-08-15", vuelo.getFechaVuelta());
    }
}
