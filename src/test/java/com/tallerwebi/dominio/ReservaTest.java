package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReservaTest {

    @Test
    public void queSeCreeReservaConDatosCorrectos() {
        Reserva reserva = new Reserva(
                "Ana",
                "ana@email.com",
                "EZE",
                "MAD",
                "2025-07-10",
                "2025-07-25",
                30.0
        );
        
        assertNull(reserva.getId());
        assertEquals("Ana", reserva.getNombre());
        assertEquals("ana@email.com", reserva.getEmail());
        assertEquals("EZE", reserva.getOrigen());
        assertEquals("MAD", reserva.getDestino());
        assertEquals("2025-07-10", reserva.getFechaIda());
        assertEquals("2025-07-25", reserva.getFechaVuelta());
        assertEquals(Double.valueOf(30.0), reserva.getPrecio());
        assertFalse(reserva.getPagado());
    }

    @Test
    public void queDefaultConstructorDejeCamposEnNull() {
        Reserva reserva = new Reserva();

        assertNull(reserva.getId());
        assertNull(reserva.getNombre());
        assertNull(reserva.getEmail());
        assertNull(reserva.getOrigen());
        assertNull(reserva.getDestino());
        assertNull(reserva.getFechaIda());
        assertNull(reserva.getFechaVuelta());
        assertNull(reserva.getPrecio());
        assertNull(reserva.getPagado());
    }

    @Test
    public void queSePuedaModificarUnaReserva() {
        Reserva reserva = new Reserva();
        reserva.setId(42L);
        reserva.setNombre("Carlos");
        reserva.setEmail("carlos@email.com");
        reserva.setOrigen("BUE");
        reserva.setDestino("ROM");
        reserva.setFechaIda("2025-08-01");
        reserva.setFechaVuelta("2025-08-15");
        reserva.setPrecio(45.5);

        assertEquals(Long.valueOf(42L), reserva.getId());
        assertEquals("Carlos", reserva.getNombre());
        assertEquals("carlos@email.com", reserva.getEmail());
        assertEquals("BUE", reserva.getOrigen());
        assertEquals("ROM", reserva.getDestino());
        assertEquals("2025-08-01", reserva.getFechaIda());
        assertEquals("2025-08-15", reserva.getFechaVuelta());
        assertEquals(Double.valueOf(45.5), reserva.getPrecio());
    }

    @Test
    public void queSePuedaAsignarYObtenerUsuario() {
        Reserva reserva = new Reserva();
        Usuario usuario = new Usuario(); // usa el constructor por defecto de Usuario
        reserva.setUsuario(usuario);
        assertSame(usuario, reserva.getUsuario());
    }
}
