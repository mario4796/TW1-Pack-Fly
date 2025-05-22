package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;

public class ControladorExcursionTest {

    private ControladorExcursion controladorExcursion;

    @BeforeEach
    public void init() {
        controladorExcursion = new ControladorExcursion();
    }

    @Test
    public void queAlEntrarALaPaginaDeExcursionesSeMuestreLaVista() {
        ModelAndView modelAndView = controladorExcursion.verExcursiones(); // nombre correcto
        assertEquals("excursiones", modelAndView.getViewName());
        assertTrue(modelAndView.getModel().get("excursiones") instanceof java.util.List);
    }
}