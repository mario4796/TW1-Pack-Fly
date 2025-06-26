package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorVuelosTest {

    private ServicioVuelos servicioVuelos;
    private ControladorVuelos controlador;
    private Model model;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void setUp() {
        servicioVuelos = mock(ServicioVuelos.class);
        controlador = new ControladorVuelos(servicioVuelos);
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void queAlBuscarUnVueloExistenteSeAgregueAlModeloYDevuelvaVistaCorrecta() {
        Vuelo vuelo = new Vuelo();
        vuelo.setPrecio(1500);

        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(vuelo);

        String vista = controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("vuelo", vuelo);
        verify(model).addAttribute("valorIda", vuelo.getPrecio());
        verify(model).addAttribute("valorVuelta", vuelo.getPrecio());
        assertEquals("busqueda-vuelo", vista);
    }

    @Test
    public void queAgregueElUsuarioSiEstaEnSesion() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        Vuelo vuelo = new Vuelo();
        vuelo.setPrecio(2000);
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(vuelo);

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("usuario", usuario);
    }

    @Test
    public void queAgregueUsuarioNullSiNoHaySesion() {
        when(session.getAttribute("USUARIO")).thenReturn(null);

        Vuelo vuelo = new Vuelo();
        vuelo.setPrecio(1800);
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(vuelo);

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("usuario", null);
    }

    @Test
    public void queAgregueErrorSiVueloNoExiste() {
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(Date.class), any(Date.class)))
                .thenReturn(null);

        controlador.buscarVuelo(
                "EZE", "MAD", new Date(), new Date(), 1000.0, 2000.0, request, model
        );

        verify(model).addAttribute("error", "Vuelo no encontrado");
    }
}
