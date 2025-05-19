package com.tallerwebi.presentacion;

import org.junit.Before;
import org.testng.annotations.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.*;

public class ControladorExcursionTest {

    private ControladorExcursion controladorExcursion;

    @Before
    public void init() {
        controladorExcursion = new ControladorExcursion();
    }

    @Test
    public void queAlEntrarALaPaginaDeExcursionesSeMuestreLaVista() {
        ModelAndView modelAndView = controladorExcursion.irAExcursiones();
        assertEquals("excursiones", modelAndView.getViewName());
    }
}