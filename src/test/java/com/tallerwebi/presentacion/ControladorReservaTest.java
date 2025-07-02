package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ControladorReservaTest {

    @Mock private ServicioReserva servicioReserva;
    @Mock private ServicioHotel servicioHotel;
    @Mock private ServicioExcursiones servicioExcursiones;
    @Mock private ServicioLogin servicioLogin;
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
    public void queSeMuestreLaVistaReservasConDatosDelUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@correo.com");

        List<Reserva> reservas = Arrays.asList(new Reserva(), new Reserva());
        List<HotelDto> hoteles = Arrays.asList(new HotelDto(), new HotelDto());
        List<Excursion> excursiones = Arrays.asList();

        // Mock del DTO de resumen
        ResumenPagoDto resumenMock = new ResumenPagoDto(100.0, 21.0, 5.0, 10.0, 116.0);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("USUARIO")).thenReturn(usuario);
        when(servicioHotel.buscarReservas(1L)).thenReturn(Arrays.asList());
        when(servicioHotel.obtenerHotelesDto(anyList())).thenReturn(hoteles);
        when(servicioReserva.obtenerReservasPorEmail(usuario.getEmail())).thenReturn(reservas);
        when(servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId())).thenReturn(excursiones);
        when(servicioLogin.obtenerDeudaDelUsuario(usuario.getId(), hoteles, reservas, excursiones)).thenReturn(resumenMock);

        String vista = controladorReserva.vistaReservas(request, model);

        verify(model).addAttribute("vuelos", reservas);
        verify(model).addAttribute("hoteles", hoteles);
        verify(model).addAttribute("excursiones", excursiones);
        verify(model).addAttribute("usuario", usuario);
        verify(model).addAttribute("subtotal", String.format("%.2f", resumenMock.getSubtotal()));
        verify(model).addAttribute("impuestos", String.format("%.2f", resumenMock.getImpuestos()));
        verify(model).addAttribute("descuentos", String.format("%.2f", resumenMock.getDescuentos()));
        verify(model).addAttribute("cargosServicio", String.format("%.2f", resumenMock.getCargosServicio()));
        verify(model).addAttribute("total", String.format("%.2f", resumenMock.getTotal()));
        verify(servicioLogin).obtenerDeudaDelUsuario(usuario.getId(), hoteles, reservas, excursiones);

        assert vista.equals("reservas");
    }
}
