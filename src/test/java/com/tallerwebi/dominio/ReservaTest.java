package com.tallerwebi.dominio;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReservaTest {

    @Test
    public void queSeCreeReservaConDatosCorrectos() {
        Reserva reserva = new Reserva("Ana", "ana@email.com", "EZE", "MAD", "2025-07-10", "2025-07-25", 30.0);

        assertEquals("Ana", reserva.getNombre());
        assertEquals("ana@email.com", reserva.getEmail());
        assertEquals("EZE", reserva.getOrigen());
        assertEquals("MAD", reserva.getDestino());
        assertEquals("2025-07-10", reserva.getFechaIda());
        assertEquals("2025-07-25", reserva.getFechaVuelta());
        assertEquals(30.0, reserva.getPrecio(), 0.001);
    }

    @Test
    public void queSePuedaModificarUnaReserva() {
        Reserva reserva = new Reserva();
        reserva.setNombre("Carlos");
        reserva.setEmail("carlos@email.com");
        reserva.setOrigen("BUE");
        reserva.setDestino("ROM");
        reserva.setFechaIda("2025-08-01");
        reserva.setFechaVuelta("2025-08-15");

        assertEquals("Carlos", reserva.getNombre());
        assertEquals("carlos@email.com", reserva.getEmail());
        assertEquals("BUE", reserva.getOrigen());
        assertEquals("ROM", reserva.getDestino());
        assertEquals("2025-08-01", reserva.getFechaIda());
        assertEquals("2025-08-15", reserva.getFechaVuelta());
    }
}
