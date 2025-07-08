package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.presentacion.dtos.VueloDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorVuelosTest {

    private ServicioReserva servicioVuelos;
    private ControladorVuelos controlador;
    private Model model;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void setUp() {
        servicioVuelos = mock(ServicioReserva.class);
        controlador = new ControladorVuelos(servicioVuelos);
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void queAlBuscarVuelosExistentesSeAgreguenAlModeloYDevuelvaVistaCorrecta() {
        VueloDTO vuelo1 = new VueloDTO("EZE", "MAD", "2025-07-10", "2025-07-20", 1500.0);
        VueloDTO vuelo2 = new VueloDTO("EZE", "MAD", "2025-07-10", "2025-07-20", 1700.0);
        List<VueloDTO> vuelos = Arrays.asList(vuelo1, vuelo2);

        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(vuelos);

        String vista = controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("vuelos", vuelos);
        assertEquals("busqueda-vuelo", vista);
    }

    @Test
    public void queAgregueElUsuarioSiEstaEnSesion() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        VueloDTO vuelo = new VueloDTO("EZE", "MAD", "2025-07-10", "2025-07-20", 2000.0);
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(Collections.singletonList(vuelo));

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("usuario", usuario);
    }

    @Test
    public void queAgregueUsuarioNullSiNoHaySesion() {
        when(session.getAttribute("USUARIO")).thenReturn(null);

        VueloDTO vuelo = new VueloDTO("EZE", "MAD", "2025-07-10", "2025-07-20", 1800.0);
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(Collections.singletonList(vuelo));

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("usuario", null);
    }

    @Test
    public void queAgregueErrorSiNoSeEncuentraNingunVuelo() {
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(Collections.emptyList());

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("error", "Vuelo no encontrado");
    }

    @Test
    public void queAgregueErrorSiNingunVueloEstaDentroDelRango() {
        VueloDTO vuelo = new VueloDTO("EZE", "MAD", "2025-07-10", "2025-07-20", 5000.0); // fuera de rango
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(Collections.singletonList(vuelo));

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("error", "No hay vuelos en el rango de precio indicado.");
    }
}