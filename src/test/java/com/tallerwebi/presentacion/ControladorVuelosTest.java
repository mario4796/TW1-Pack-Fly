package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControladorVuelosTest {

    @Mock private ServicioVuelos   servicioVuelos;
    @Mock private ServicioReserva  servicioReserva;
    @Mock private ServicioEmail    servicioEmail;

    @InjectMocks private ControladorVuelos controlador;

    private HttpServletRequest        request;
    private HttpSession               session;
    private Model                     model;
    private RedirectAttributesModelMap redirectAttributes;
    private Usuario                   usuario;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request            = mock(HttpServletRequest.class);
        session            = mock(HttpSession.class);
        model              = mock(Model.class);
        redirectAttributes = new RedirectAttributesModelMap();

        when(request.getSession()).thenReturn(session);

        usuario = new Usuario();
        usuario.setId(7L);
        usuario.setEmail("user@vuelo.com");
    }

    /** GET /busqueda-vuelo — usuario NO logueado */
    @Test
    public void vistaBusquedaVuelo_usuarioNoLogueado_modelUsuarioNulo() {
        when(session.getAttribute("USUARIO")).thenReturn(null);

        String vista = controlador.vistaBusquedaVuelo(request, model);

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("usuario", null);
    }

    /** GET /busqueda-vuelo — usuario logueado */
    @Test
    public void vistaBusquedaVuelo_usuarioLogueado_modelUsuario() {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String vista = controlador.vistaBusquedaVuelo(request, model);

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("usuario", usuario);
    }

    /** POST /busqueda-vuelo — encontró vuelo sin filtros de precio */
    @Test
    public void buscarVuelo_encontroVuelo_sinFiltros() {
        Date ida    = new GregorianCalendar(2025, Calendar.JULY, 10).getTime();
        Date vuelta = new GregorianCalendar(2025, Calendar.JULY, 20).getTime();
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        Vuelo vuelo = new Vuelo();
        vuelo.setPrecio(200);
        when(servicioVuelos.getVuelo("EZE", "MAD", ida, vuelta)).thenReturn(vuelo);

        String vista = controlador.buscarVuelo(
                "EZE", "MAD", ida, vuelta,
                null, null,
                request, model
        );

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("usuario", usuario);
        verify(servicioVuelos).getVuelo("EZE", "MAD", ida, vuelta);
        verify(model).addAttribute("vuelo", vuelo);
        verify(model).addAttribute("vueloUrl", true);
        verify(model).addAttribute("valorIda", vuelo.getPrecio());
        verify(model).addAttribute("valorVuelta", vuelo.getPrecio());
        verify(model).addAttribute("precioMin", null);
        verify(model).addAttribute("precioMax", null);
        verify(model).addAttribute("origen", "EZE");
        verify(model).addAttribute("destino", "MAD");
        verify(model).addAttribute("fechaIda", ida);
        verify(model).addAttribute("fechaVuelta", vuelta);
    }

    /** POST /busqueda-vuelo — no encontró vuelo */
    @Test
    public void buscarVuelo_noEncontroVuelo_muestraError() {
        Date ida    = new GregorianCalendar(2025, Calendar.JULY, 10).getTime();
        Date vuelta = new GregorianCalendar(2025, Calendar.JULY, 20).getTime();
        when(session.getAttribute("USUARIO")).thenReturn(usuario);
        when(servicioVuelos.getVuelo(anyString(), anyString(), any(), any())).thenReturn(null);

        String vista = controlador.buscarVuelo(
                "X", "Y", ida, vuelta,
                50.0, 150.0,
                request, model
        );

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("usuario", usuario);
        verify(model).addAttribute("error", "Vuelo no encontrado");
        verify(model).addAttribute("precioMin", 50.0);
        verify(model).addAttribute("precioMax", 150.0);
        verify(model).addAttribute("origen", "X");
        verify(model).addAttribute("destino", "Y");
        verify(model).addAttribute("fechaIda", ida);
        verify(model).addAttribute("fechaVuelta", vuelta);
    }

    /** GET /formulario-reserva — muestra formulario vacío */
    @Test
    public void mostrarFormularioVacio_devuelveFormularioReserva() {
        String vista = controlador.mostrarFormularioVacio();
        assertEquals("formularioReserva", vista);
    }

    /** POST /formulario-reserva — copia parámetros al modelo */
    @Test
    public void mostrarFormularioReserva_populaModelo() {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String vista = controlador.mostrarFormularioReserva(
                "EZE", "MAD", "2025-07-10", "2025-07-20", 150.0,
                request, model
        );

        assertEquals("formularioReserva", vista);
        verify(model).addAttribute("usuario", usuario);
        verify(model).addAttribute("origen", "EZE");
        verify(model).addAttribute("destino", "MAD");
        verify(model).addAttribute("fechaIda", "2025-07-10");
        verify(model).addAttribute("fechaVuelta", "2025-07-20");
        verify(model).addAttribute("precio", 150.0);
    }

    /** POST /guardar-reserva — éxito redirige limpio */
    @Test
    public void guardarReserva_exito_redireccionYGuardaEntidad() throws MessagingException {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);

        String vista = controlador.guardarReserva(
                "Ana", "ana@mail.com",
                "EZE", "MAD",
                "2025-07-10", "2025-07-20",
                120.0,
                request, redirectAttributes, model
        );

        // Ahora la implementación redirige sin query param
        assertEquals("redirect:/busqueda-hoteles", vista);

        verify(servicioReserva).guardarReserva(argThat(res ->
                res.getNombre().equals("Ana") &&
                        res.getEmail().equals("ana@mail.com") &&
                        res.getOrigen().equals("EZE") &&
                        res.getDestino().equals("MAD") &&
                        res.getFechaIda().equals("2025-07-10") &&
                        res.getFechaVuelta().equals("2025-07-20") &&
                        res.getPrecio().equals(120.0) &&
                        res.getUsuario().equals(usuario)
        ));
    }

    /** POST /guardar-reserva — error redirige y notifica */
    @Test
    public void guardarReserva_error_redirigeYFlashWarning() throws MessagingException {
        when(session.getAttribute("USUARIO")).thenReturn(usuario);
        doThrow(new RuntimeException("boom")).when(servicioReserva).guardarReserva(any());

        String vista = controlador.guardarReserva(
                "Ana", "ana@mail.com",
                "EZE", "MAD",
                "2025-07-10", "2025-07-20",
                120.0,
                request, redirectAttributes, model
        );

        assertEquals("redirect:/busqueda-hoteles", vista);
        assertEquals("Hubo un error al crear la reserva de vuelo.",
                redirectAttributes.getFlashAttributes().get("mensaje"));
        assertEquals("warning",
                redirectAttributes.getFlashAttributes().get("tipo"));
    }
}
