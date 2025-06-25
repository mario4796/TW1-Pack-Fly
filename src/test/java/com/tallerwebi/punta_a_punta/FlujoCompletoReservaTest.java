package com.tallerwebi.punta_a_punta;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.presentacion.ControladorReserva;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FlujoCompletoReservaTest {

    @Mock private ServicioLogin servicioLogin;
    @Mock private ServicioReserva servicioReserva;
    @Mock private ServicioHotel servicioHotel;
    @Mock private ServicioExcursiones servicioExcursiones;

    @Mock private HttpServletRequest request;
    @Mock private HttpSession session;
    @Mock private Model model;

    @InjectMocks
    private ControladorReserva controladorReserva;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFlujoCompletoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@correo.com");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        List<Reserva> reservasVuelo = Arrays.asList(new Reserva());
        when(servicioReserva.obtenerReservasPorEmail(usuario.getEmail())).thenReturn(reservasVuelo);

        List<Hotel> hoteles = Arrays.asList(new Hotel());
        List<HotelDto> hotelesDto = Arrays.asList(new HotelDto());
        when(servicioHotel.buscarReservas(usuario.getId())).thenReturn(hoteles);
        when(servicioHotel.obtenerHotelesDto(hoteles)).thenReturn(hotelesDto);

        List<Excursion> excursiones = Collections.emptyList();
        when(servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId())).thenReturn(excursiones);


        String vista = controladorReserva.vistaReservas(request, model);


        verify(model).addAttribute("vuelos", reservasVuelo);
        verify(model).addAttribute("hoteles", hotelesDto);
        verify(model).addAttribute("excursiones", excursiones);
        verify(model).addAttribute("usuario", usuario);
        assertEquals("reservas", vista);
    }
}
