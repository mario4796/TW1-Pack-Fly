package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.ControladorReserva;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControladorReservaTest {

    @Mock private ServicioHotel       hotelService;
    @Mock private ServicioReserva     servicioReserva;
    @Mock private ServicioExcursiones servicioExcursiones;
    @Mock private ServicioLogin       servicioLogin;
    @Mock private ServicioEmail       servicioEmail;

    @InjectMocks private ControladorReserva controlador;

    private HttpServletRequest request;
    private HttpSession        session;
    private Model              model;
    private RedirectAttributes flash;

    private Usuario usuario;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        model   = mock(Model.class);
        flash   = new RedirectAttributesModelMap();

        when(request.getSession()).thenReturn(session);

        usuario = new Usuario();
        usuario.setId(99L);
        usuario.setNombre("TestUser");
        usuario.setEmail("test@usuario.com");
    }

    /**
     * GET /reservas — usuario logueado
     */
    @Test
    public void vistaReservas_usuarioLogueado_populaModeloYDevuelveVista() throws MessagingException {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        // datos de retorno de servicios
        List<Hotel>   hotelesEnt     = Collections.singletonList(new Hotel());
        List<HotelDto> hotelesDto    = Collections.singletonList(new HotelDto());
        List<Reserva> vuelosEnt      = Collections.singletonList(new Reserva());
        List<Excursion> excursionesEnt = Collections.singletonList(new Excursion());
        ResumenPagoDto resumen       = new ResumenPagoDto(100, 10, 5, 0, 115);

        when(hotelService.buscarReservas(usuario.getId())).thenReturn(hotelesEnt);
        when(hotelService.obtenerHotelesDto(hotelesEnt)).thenReturn(hotelesDto);
        when(servicioReserva.obtenerReservasPorEmail(usuario.getEmail())).thenReturn(vuelosEnt);
        when(servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId())).thenReturn(excursionesEnt);
        when(servicioLogin.obtenerDeudaDelUsuario(
                eq(usuario.getId()), eq(hotelesDto), eq(vuelosEnt), eq(excursionesEnt))
        ).thenReturn(resumen);

        String vista = controlador.vistaReservas(request, model);

        assertEquals("reservas", vista);
        verify(model).addAttribute("vuelos", vuelosEnt);
        verify(model).addAttribute("hoteles", hotelesDto);
        verify(model).addAttribute("excursiones", excursionesEnt);
        verify(model).addAttribute("usuario", usuario);
        verify(model).addAttribute("subtotal",    String.format("%.2f", resumen.getSubtotal()));
        verify(model).addAttribute("impuestos",   String.format("%.2f", resumen.getImpuestos()));
        verify(model).addAttribute("descuentos",  String.format("%.2f", resumen.getDescuentos()));
        verify(model).addAttribute("cargosServicio", String.format("%.2f", resumen.getCargosServicio()));
        verify(model).addAttribute("total",       String.format("%.2f", resumen.getTotal()));
        verify(servicioLogin).obtenerDeudaDelUsuario(usuario.getId(), hotelesDto, vuelosEnt, excursionesEnt);
    }

    /**
     * GET /reservas — usuario NO logueado
     */
    @Test
    public void vistaReservas_usuarioNoLogueado_modelUsuarioNulo() {
        when(session.getAttribute("USUARIO")).thenReturn(null);

        String vista = controlador.vistaReservas(request, model);

        assertEquals("reservas", vista);
        verify(model).addAttribute("usuario", null);
        verifyNoMoreInteractions(hotelService, servicioReserva, servicioExcursiones, servicioLogin);
    }

    /**
     * POST /eliminarReservaHotel — éxito
     */
    @Test
    public void eliminarReservaHotel_success_redireccionYFlashSuccess() throws MessagingException {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String resultado = controlador.eliminarReservaHotel("HotelTest", request, flash);

        assertEquals("redirect:/reservas", resultado);
        assertEquals("Reserva de hotel eliminada con éxito.", flash.getFlashAttributes().get("mensaje"));
        assertEquals("success", flash.getFlashAttributes().get("tipo"));
        verify(hotelService).eliminarReserva(usuario.getId(), "HotelTest");
    }

    /**
     * POST /eliminarReservaHotel — error
     */
    @Test
    public void eliminarReservaHotel_error_redireccionYFlashWarning() throws MessagingException {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);
        doThrow(new RuntimeException("boom")).when(hotelService).eliminarReserva(anyLong(), anyString());

        String resultado = controlador.eliminarReservaHotel("HotelX", request, flash);

        assertEquals("redirect:/reservas", resultado);
        assertEquals("Hubo un error al eliminar la reserva de hotel.", flash.getFlashAttributes().get("mensaje"));
        assertEquals("warning", flash.getFlashAttributes().get("tipo"));
    }

    /**
     * POST /eliminarReservaVuelo — éxito
     */
    @Test
    public void eliminarReservaVuelo_success_redireccionYFlashSuccess() {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String resultado = controlador.eliminarReservaVuelo(
                usuario.getEmail(),
                "2025-08-01",
                "2025-08-15",
                request,
                flash
        );

        assertEquals("redirect:/reservas", resultado);
        assertEquals("Reserva de vuelo eliminada con éxito.", flash.getFlashAttributes().get("mensaje"));
        assertEquals("success", flash.getFlashAttributes().get("tipo"));
        // la implementación invierte fechaIda y fechaVuelta al llamar al servicio
        verify(servicioReserva).eliminarReserva(
                usuario.getEmail(),
                "2025-08-15",
                "2025-08-01"
        );
    }

    /**
     * POST /eliminarReservaVuelo — error
     */
    @Test
    public void eliminarReservaVuelo_error_redireccionYFlashWarning() {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);
        doThrow(new RuntimeException()).when(servicioReserva).eliminarReserva(anyString(), anyString(), anyString());

        String resultado = controlador.eliminarReservaVuelo(
                usuario.getEmail(), "X", "Y", request, flash
        );

        assertEquals("redirect:/reservas", resultado);
        assertEquals("Hubo un error al eliminar la reserva de vuelo.", flash.getFlashAttributes().get("mensaje"));
        assertEquals("warning", flash.getFlashAttributes().get("tipo"));
    }

    /**
     * POST /eliminarReservaExcursion — éxito
     */
    @Test
    public void eliminarReservaExcursion_success_redireccionYFlashSuccess() {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String resultado = controlador.eliminarReservaExcursion("ExcursionTitle", request, flash);

        assertEquals("redirect:/reservas", resultado);
        // el controlador usa "excursion" sin tilde
        assertEquals("Reserva de excursion eliminada con éxito.", flash.getFlashAttributes().get("mensaje"));
        assertEquals("success", flash.getFlashAttributes().get("tipo"));
        verify(servicioExcursiones).eliminarReserva(usuario.getId(), "ExcursionTitle");
    }

    /**
     * POST /eliminarReservaExcursion — error
     */
    @Test
    public void eliminarReservaExcursion_error_redireccionYFlashWarning() {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);
        doThrow(new RuntimeException()).when(servicioExcursiones).eliminarReserva(anyLong(), anyString());

        String resultado = controlador.eliminarReservaExcursion("X", request, flash);

        assertEquals("redirect:/reservas", resultado);
        // idem, sin tilde
        assertEquals("Hubo un error al eliminar la reserva de excursion.", flash.getFlashAttributes().get("mensaje"));
        assertEquals("warning", flash.getFlashAttributes().get("tipo"));
    }
}
