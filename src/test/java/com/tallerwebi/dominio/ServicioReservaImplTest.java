package com.tallerwebi.dominio;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServicioReservaImplTest {

    private ServicioReservaImpl servicioReserva;

    @Mock
    private RepositorioReserva repositorioReserva;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servicioReserva = new ServicioReservaImpl(repositorioReserva);
    }

    @Test
    public void queSePuedaGuardarUnaReserva() {
        Reserva reserva = new Reserva();
        servicioReserva.guardarReserva(reserva);

        verify(repositorioReserva, times(1)).guardar(reserva);
    }

    @Test
    public void queSePuedaObtenerReservasPorEmail() {
        String email = "usuario@ejemplo.com";
        Reserva r1 = new Reserva();
        Reserva r2 = new Reserva();
        List<Reserva> reservas = Arrays.asList(r1, r2);

        when(repositorioReserva.buscarPorEmail(email)).thenReturn(reservas);

        List<Reserva> resultado = servicioReserva.obtenerReservasPorEmail(email);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(r1));
        assertTrue(resultado.contains(r2));
        verify(repositorioReserva, times(1)).buscarPorEmail(email);
    }
} 
