package com.tallerwebi.dominio;

import com.tallerwebi.config.ConfiguracionDeApiKey;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.implementaciones.ServicioHotelImpl;
import com.tallerwebi.infraestructura.implementaciones.RepositorioHotelImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServicioHotelImplTest {

    private RepositorioHotelImp mockRepositorio;
    private ServicioHotelImpl servicioHotel;
    private ConfiguracionDeApiKey config;

    @BeforeEach
    void setUp() {
        mockRepositorio = mock(RepositorioHotelImp.class);
        servicioHotel = new ServicioHotelImpl(config, mockRepositorio);
    }

    @Test
    void queAlBuscarReservasDevuelvaLasDelRepositorio() {
        Long usuarioId = 1L;
        List<Hotel> reservasEsperadas = List.of(new Hotel(), new Hotel());
        when(mockRepositorio.buscarReserva(usuarioId)).thenReturn(reservasEsperadas);

        List<Hotel> resultado = servicioHotel.buscarReservas(usuarioId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(reservasEsperadas, resultado);
    }

    @Test
    void queAlReservarUnHotelNoDevuelvaNada() {
        Hotel hotel = new Hotel();

        assertDoesNotThrow(() -> servicioHotel.reserva(hotel));
    }


    /* Estos tests consumen consultas de la API!!! Descomentar para usarlos
    @Test
    void queAlBuscarUnHotelMeDeberiaDevolverElResultadoDeLaBusquedaCorrectamente() {
        String ciudad = "Buenos Aires";
        String checkIn = "2025-10-15";
        String checkOut = "2025-10-17";
        int adults = 2;
        int children = 0;
        String children_ages = "";

        List<HotelDto> resultado = servicioHotel.buscarHoteles(ciudad, checkIn, checkOut, adults, children, children_ages);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void queAlBuscarUnHotelConFechasIncorrectosArrojaUnError() {
        String ciudad = "Buenos Aires";
        String checkIn = "2025-05-15";
        String checkOut = "2025-05-17";
        int adults = 2;
        int children = 0;
        String children_ages = "";

        List<HotelDto> resultado;

        try {
            resultado = servicioHotel.buscarHoteles(ciudad, checkIn, checkOut, adults, children, children_ages);
        } catch (Exception e) {
            resultado = List.of();
        }

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
*/
        /*
        El try and catch ponerlo en ServicioHotelImpl cuando esté terminado
        y agregar mensaje de error o validación en la vista.
         */
}