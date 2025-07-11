package com.tallerwebi.dominio;
/*
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.implementaciones.ServicioReservaImpl;
import com.tallerwebi.infraestructura.RepositorioReserva;
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
        Vuelo vuelo = new Vuelo();
        servicioReserva.guardarReserva(vuelo);

        verify(repositorioReserva, times(1)).guardar(vuelo);
    }

    @Test
    public void queSePuedaObtenerReservasPorEmail() {
        String email = "usuario@ejemplo.com";
        Vuelo r1 = new Vuelo();
        Vuelo r2 = new Vuelo();
        List<Vuelo> vuelos = Arrays.asList(r1, r2);

        when(repositorioReserva.buscarPorEmail(email)).thenReturn(vuelos);

        List<Vuelo> resultado = servicioReserva.obtenerReservasPorEmail(email);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(r1));
        assertTrue(resultado.contains(r2));
        verify(repositorioReserva, times(1)).buscarPorEmail(email);
    }
} 
*/