package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioReserva;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControladorReservaTest {

    private ControladorReserva controlador;

    @Mock
    private ServicioReserva servicioReserva;

    @Mock
    private Model model;

    /*@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controlador = new ControladorReserva(servicioReserva);
    }

    @Test
    public void queSeGuardeUnaReservaCorrectamente() {
        String nombre = "Juan";
        String email = "juan@correo.com";
        String origen = "Buenos Aires";
        String destino = "Madrid";
        String fechaIda = "2025-07-01";
        String fechaVuelta = "2025-07-15";

        String vista = controlador.guardarReserva(nombre, email, origen, destino, fechaIda, fechaVuelta, model);

        verify(servicioReserva).guardarReserva(any(Reserva.class));
        assertEquals("reservaExitosa", vista);
    }

    @Test
    public void queSePuedanVerReservasPorEmail() {
        String email = "test@correo.com";
        List<Reserva> reservas = Arrays.asList(new Reserva(), new Reserva());
        when(servicioReserva.obtenerReservasPorEmail(email)).thenReturn(reservas);

        String vista = controlador.verReservas(email, model);

        verify(servicioReserva).obtenerReservasPorEmail(email);
        verify(model).addAttribute("reservas", reservas);
        verify(model).addAttribute("email", email);
        assertEquals("verReservas", vista);
    }*/
}
