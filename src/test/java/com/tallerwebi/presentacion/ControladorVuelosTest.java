package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.Vuelo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControladorVuelosTest {

    private ServicioVuelos servicioVuelos;
    private ControladorVuelos controlador;
    private Model model;

    @Before
    public void setUp() {
        servicioVuelos = mock(ServicioVuelos.class);
        controlador = new ControladorVuelos(servicioVuelos);
        model = mock(Model.class);
    }

    @Test
    public void queLaVistaBusquedaVueloDevuelvaElNombreDeLaVista() {
        String vista = controlador.vistaBusquedaVuelo();
        assertEquals("busqueda-vuelo", vista);
    }

    @Test
    public void queAlBuscarUnVueloExistenteSeAgregueElVueloAlModelo() {
        String origen = "EZE";
        String destino = "MAD";
        Date fechaIda = new Date();
        Date fechaVuelta = new Date();
        Vuelo vuelo = new Vuelo();
        vuelo.setPrecio(1000);

        when(servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta)).thenReturn(vuelo);

        String vista = controlador.buscarVuelo(origen, destino, fechaIda, fechaVuelta, model);

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("vuelo", vuelo);
        verify(model).addAttribute("vueloUrl", true);
        verify(model).addAttribute("valorIda", vuelo.getPrecio());
        verify(model).addAttribute("valorVuelta", vuelo.getPrecio());
        verify(model, never()).addAttribute(eq("error"), anyString());
    }

    @Test
    public void queSiNoSeEncuentraVueloSeAgregueUnErrorAlModelo() {
        String origen = "EZE";
        String destino = "MAD";
        Date fechaIda = new Date();
        Date fechaVuelta = new Date();

        when(servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta)).thenReturn(null);

        String vista = controlador.buscarVuelo(origen, destino, fechaIda, fechaVuelta, model);

        assertEquals("busqueda-vuelo", vista);
        verify(model).addAttribute("error", "Vuelo no encontrado");
        verify(model, never()).addAttribute(eq("vuelo"), any());
    }
}