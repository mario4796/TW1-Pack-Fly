package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.implementaciones.ServicioExcursionesImpl;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorExcursionTest {

    private ServicioExcursionesImpl servicioExcursiones;
    private ControladorExcursion controladorExcursion;
    private Model model;
    private HttpSession session;
    private HttpServletRequest request;

    @Before
    public void init() {
        servicioExcursiones = mock(ServicioExcursionesImpl.class);
        controladorExcursion = new ControladorExcursion(servicioExcursiones);
        model = mock(Model.class);
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void queAlSolicitarExcursionesDevuelvaLaVistaCorrecta() {
        List<ExcursionDTO> excursiones = new ArrayList<>();
        when(servicioExcursiones.getExcursiones(anyString(), anyString())).thenReturn(excursiones);

        String vista = controladorExcursion.verExcursiones(
                "Buenos Aires", "excursiones", 0.0, 99999.0, model, request);

        assertEquals("excursiones", vista);
    }

    @Test
    public void queAlSolicitarExcursionesAgregueLaListaAlModelo() {
        List<ExcursionDTO> excursiones = new ArrayList<>();

        Excursion excursion = new Excursion();
        excursion.setTitle("Tour por la ciudad");
        excursion.setStartDate("2025-07-15");
        excursion.setLocation("Buenos Aires");
        excursion.setDescription("Recorrido por los puntos principales");
        excursion.setUrl("https://ejemplo.com/tour");
        excursion.setPrecio(1500.0);

        excursiones.add(new ExcursionDTO(excursion));

        when(servicioExcursiones.getExcursiones(anyString(), anyString())).thenReturn(excursiones);

        controladorExcursion.verExcursiones(
                "Buenos Aires", "tour", 0.0, 99999.0, model, request);

        verify(model).addAttribute("excursiones", excursiones);
    }

    @Test
    public void queAgregueUsuarioLogueadoAlModeloCuandoExisteSesion() {
        Usuario usuarioMock = mock(Usuario.class);
        when(session.getAttribute("USUARIO")).thenReturn(usuarioMock);

        controladorExcursion.verExcursiones(
                "Buenos Aires", "query", 0.0, 99999.0, model, request);

        verify(model).addAttribute("usuario", usuarioMock);
    }

    @Test
    public void queNoAgregueUsuarioLogueadoAlModeloCuandoNoHaySesion() {
        when(session.getAttribute("USUARIO")).thenReturn(null);

        controladorExcursion.verExcursiones(
                "Buenos Aires", "query", 0.0, 99999.0, model, request);

        verify(model).addAttribute("usuario", null);
    }
}
