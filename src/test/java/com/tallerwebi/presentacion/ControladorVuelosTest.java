package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.Vuelo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControladorVuelosTest {

    private ServicioVuelos servicioVuelos;
    @Mock
    private ServicioReserva servicioReserva;

    private ControladorVuelos controladorR;
    private ControladorVuelos controladorV;
    private Model model;

    @Mock
    private Model modelMock;

    @BeforeEach
    public void setUp() {
        servicioVuelos = mock(ServicioVuelos.class);
        servicioReserva = mock(ServicioReserva.class);
        controladorV = new ControladorVuelos(servicioVuelos);
        controladorR = new ControladorVuelos(servicioReserva);
        model = mock(Model.class);
    }

    @Test
    public void queLaVistaBusquedaVueloDevuelvaElNombreDeLaVista() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(null);

        String vista = controladorV.vistaBusquedaVuelo(request, model);
        assertEquals("busqueda-vuelo", vista);
    }
/*
    @Test
    public void queAlBuscarUnVueloExistenteSeAgregueElVueloAlModelo() {
        String origen = "EZE";
        String destino = "MAD";
        Date fechaIda = new Date();
        Date fechaVuelta = new Date();
        Vuelo vuelo = new Vuelo();
        vuelo.setPrecio(1000);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(null);

        when(servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta)).thenReturn(vuelo);

        //String vista = controladorV.buscarVuelo(origen, destino, fechaIda, fechaVuelta, request, model);

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("usuario", null);
        verify(model).addAttribute("vuelo", vuelo);
        verify(model).addAttribute("vueloUrl", true);
        verify(model).addAttribute("valorIda", vuelo.getPrecio());
        verify(model).addAttribute("valorVuelta", vuelo.getPrecio());
        verify(model, never()).addAttribute(eq("error"), anyString());
    }


    @Test
    public void queSiNoSeEncuentraVueloSeAgregueUnErrorAlModelo() {
        String origen = "EZE";
        String destino = "MAD";
        Date fechaIda = new Date();
        Date fechaVuelta = new Date();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(null);

        when(servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta)).thenReturn(null);

        String vista = controladorV.buscarVuelo(origen, destino, fechaIda, fechaVuelta, request, model);

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("usuario", null);
        verify(model).addAttribute("error", "Vuelo no encontrado");
        verify(model, never()).addAttribute(eq("vuelo"), any());
    }
*/
/*
    @Test
    public void queSeGuardeUnaReservaCorrectamente() {
        String nombre = "Juan";
        String email = "juan@correo.com";
        String origen = "Buenos Aires";
        String destino = "Madrid";
        String fechaIda = "2025-07-01";
        String fechaVuelta = "2025-07-15";
        Double precio = 30.0;

        String vista = controladorR.guardarReserva(nombre, email, origen, destino, fechaIda, fechaVuelta, precio, modelMock);

        verify(servicioReserva).guardarReserva(any(Reserva.class));
        assertEquals("redirect:/busqueda-hoteles?reservaExitosa=true", vista);
    }*/

    /*@Test
    public void queSePuedanVerReservasPorEmail() {
        String email = "test@correo.com";
        List<Reserva> reservas = Arrays.asList(new Reserva(), new Reserva());
        when(servicioReserva.obtenerReservasPorEmail(email)).thenReturn(reservas);

        String vista = controladorR.verReservas(email, model);

        verify(servicioReserva).obtenerReservasPorEmail(email);
        verify(model).addAttribute("reservas", reservas);
        verify(model).addAttribute("email", email);
        assertEquals("verReservas", vista);
    }*/
}