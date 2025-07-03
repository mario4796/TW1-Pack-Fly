package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorHotelTest {

    private ServicioHotel servicioHotel;
    private Model model;
    private HttpSession session;
    private HttpServletRequest request;
    private ServicioPreferenciaUsuario servicioPreferenciaUsuario;
    //private IconHelper iconHelper;
    private ControladorHotel controladorHotel;
    //private Hotel hotel;

    @Before
    public void init() {
        servicioHotel = mock(ServicioHotel.class);
        model = mock(Model.class);
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
        servicioPreferenciaUsuario = mock(ServicioPreferenciaUsuario.class);
        //iconHelper = mock(IconHelper.class);
        controladorHotel = new ControladorHotel(servicioHotel, servicioPreferenciaUsuario);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(null);
    }

    @Test
    public void queAlSolicitarUnHotelDevuelvaLaVistaCorrecta(){
        List<HotelDto> hoteles = new ArrayList<>();
        when(servicioHotel.buscarHoteles(anyString(),anyString(),anyString(),anyInt(), anyInt())).thenReturn(hoteles);

        String vista = controladorHotel.buscar(
                "Mendoza",
                "30/06/2025",
                "07/07/2025",
                1,
                0,
                12000.0,
                15000.0,
                request,
                model);

        assertEquals("busqueda-hoteles", vista);
    }

    @Test
    public void queSePuedaReservarUnHotelCorrectamenteYDevuelvaLaVistaCorrecta() throws MessagingException {
        Usuario usuario = new Usuario();
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String vista = controladorHotel.reservar(
                "Hotel test",
                "Mendoza",
                "30/06/2025",
                "07/07/2025",
                1,
                0,
                12000.0,
                redirectAttributes,
                request);

        assertEquals("redirect:/busqueda-excursiones", vista);

        verify(servicioHotel).reserva(any(Hotel.class));
        verify(servicioPreferenciaUsuario).registrarReservaHotel(eq(usuario), eq(1));
    }
}
