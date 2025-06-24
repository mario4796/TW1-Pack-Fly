package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorExcursionTest {

    private ServicioExcursionesImpl servicioExcursiones;
    private ControladorExcursion controladorExcursion;
    private Model model;
    private HttpSession session;
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void init() {
        servicioExcursiones = mock(ServicioExcursionesImpl.class);
        controladorExcursion = new ControladorExcursion(servicioExcursiones);
        model = mock(Model.class);
        session = mock(HttpSession.class);
        redirectAttributes = mock(RedirectAttributes.class);
    }
/*
    @Test
    public void queAlSolicitarExcursionesDevuelvaLaVistaCorrecta() {
        // preparación
        List<ExcursionDTO> excursiones = new ArrayList<>();
        when(servicioExcursiones.getExcursiones(anyString(), anyString())).thenReturn(excursiones);

        // ejecución
        String vista = controladorExcursion.verExcursiones("Buenos Aires", "excursiones", model, session);

        // verificación
        assertEquals("excursiones", vista);
    }

    @Test
    public void queAlSolicitarExcursionesAgregueLaListaAlModelo() {
        // preparación
        List<ExcursionDTO> excursiones = new ArrayList<>();

        // Primero crear la entidad Excursion
        Excursion excursion = new Excursion();
        excursion.setTitle("Tour por la ciudad");
        excursion.setStartDate("2025-07-15");
        excursion.setLocation("Buenos Aires");
        excursion.setDescription("Recorrido por los puntos principales");
        excursion.setUrl("https://ejemplo.com/tour");
        excursion.setPrecio(1500.0);

        // Luego crear el DTO usando el constructor
        excursiones.add(new ExcursionDTO(excursion));

        when(servicioExcursiones.getExcursiones(anyString(), anyString())).thenReturn(excursiones);

        // ejecución y verificación
        controladorExcursion.verExcursiones("Buenos Aires", "excursiones", model, session);
        verify(model).addAttribute("excursiones", excursiones);
    }

    @Test
    public void queAgregueUsuarioLogueadoAlModeloCuandoExisteSesion() {
        // preparación
        Usuario usuarioMock = mock(Usuario.class);
        when(session.getAttribute("USUARIO")).thenReturn(usuarioMock);

        // ejecución
        controladorExcursion.verExcursiones("Buenos Aires", "excursiones", model, session);

        // verificación
        verify(model).addAttribute("usuario", usuarioMock);
    }

    @Test
    public void queNoAgregueUsuarioLogueadoAlModeloCuandoNoHaySesion() {
        // preparación
        when(session.getAttribute("USUARIO")).thenReturn(null);

        // ejecución
        controladorExcursion.verExcursiones("Buenos Aires", "excursiones", model, session);

        // verificación
        verify(model).addAttribute("usuario", null);
    }

 */
}
