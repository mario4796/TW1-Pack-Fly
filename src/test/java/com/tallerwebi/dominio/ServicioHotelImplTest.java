package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServicioHotelImplTest {

    private final ServicioHotelImpl servicioHotel = new ServicioHotelImpl();

    // Descomentar los test para probarlos (CONSUMEN CONSULTAS DE LA API!!!)
    /*
    @Test
    void queAlBuscarUnHotelMeDeberiaDevolverElResultadoDeLaBusquedaCorrectamente() {
        String ciudad = "Buenos Aires";
        String checkIn = "2025-10-15";
        String checkOut = "2025-10-17";
        int adults = 2;
        int children = 0;
        String children_ages = "";

        List<Hotel> resultado = servicioHotel.buscarHoteles(ciudad, checkIn, checkOut, adults, children, children_ages);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }
*/
/*
    @Test
    void queAlBuscarUnHotelConFechasIncorrectosArrojaUnError() {
        String ciudad = "Buenos Aires";
        String checkIn = "2025-05-15";
        String checkOut = "2025-05-17";
        int adults = 2;
        int children = 0;
        String children_ages = "";

        List<Hotel> resultado;

        try {
            resultado = servicioHotel.buscarHoteles(ciudad, checkIn, checkOut, adults, children, children_ages);
        } catch (Exception e) {
            resultado = List.of();
        }

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
*/
        /*/////////////////////////////////////////////////////////////////
        El try and catch ponerlo en ServicioHotelImpl cuando esté terminado
        y agregar mensaje de error o validación en la vista.
        //////////////////////////////////////////////////////////////// */
}