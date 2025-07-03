package com.tallerwebi.dominio;

import com.tallerwebi.dominio.implementaciones.ServicioReservaImpl;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioReserva;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServicioReservaImplTest {

    @Mock
    private RepositorioReserva repositorioReserva;

    @Mock
    private ServicioPreferenciaUsuario servicioPreferenciaUsuario;

    @InjectMocks
    private ServicioReservaImpl servicioReserva;


    /**
     * Si no hay usuario, sólo guarda en repo y no llama a preferencias.
     */
    @Test
    public void guardarReserva_sinUsuario_soloInvocaRepo() {
        Reserva reserva = new Reserva();

        servicioReserva.guardarReserva(reserva);

        verify(repositorioReserva).guardar(reserva);
        verifyNoInteractions(servicioPreferenciaUsuario);
    }

    @Test
    public void eliminarReserva_delegacionAlRepo() {
        servicioReserva.eliminarReserva("foo@bar", "2025-01-01", "2025-01-05");
        verify(repositorioReserva).eliminarReserva("foo@bar", "2025-01-01", "2025-01-05");
    }

    @Test
    public void editarReserva_existente_actualizaYGuarda() {
        Reserva r = new Reserva();
        r.setOrigen("oldO");
        r.setDestino("oldD");
        r.setFechaIda("oldI");
        r.setFechaVuelta("oldV");

        when(repositorioReserva.buscarPorIdyEmail("e@x", 10L)).thenReturn(r);

        servicioReserva.editarReserva(10L, "e@x", "newO", "newD", "newI", "newV");

        assertEquals("newO", r.getOrigen());
        assertEquals("newD", r.getDestino());
        assertEquals("newI", r.getFechaIda());
        assertEquals("newV", r.getFechaVuelta());
        verify(repositorioReserva).actualizar(r);
    }

    @Test
    public void editarReserva_noExistente_noActualiza() {
        when(repositorioReserva.buscarPorIdyEmail(anyString(), anyLong())).thenReturn(null);

        servicioReserva.editarReserva(5L, "a@b", "O", "D", "I", "V");

        verify(repositorioReserva, never()).actualizar(any(Reserva.class));
    }

    @Test
    public void buscarPorId_delegacionAlRepo() {
        Reserva r = new Reserva();
        when(repositorioReserva.buscarPorId(99L)).thenReturn(r);

        assertSame(r, servicioReserva.buscarPorId(99L));
    }

    @Test
    public void contarReservasUltimosDias_filtraCorrectamente() {
        LocalDate hoy = LocalDate.now();
        Reserva mañana = new Reserva(); mañana.setFechaIda(hoy.plusDays(1).toString());
        Reserva ayer   = new Reserva(); ayer.setFechaIda(hoy.minusDays(1).toString());

        when(repositorioReserva.buscarPorEmail("u@x"))
                .thenReturn(Arrays.asList(mañana, ayer));

        // Sólo cuenta las fechas posteriores a hoy
        assertEquals(1L, servicioReserva.contarReservasUltimosDias("u@x", 0));
        assertEquals(1L, servicioReserva.contarReservasUltimosDias("u@x", 1));
    }

    @Test
    public void pagarReservas_delegacionAlRepo() {
        servicioReserva.pagarRerservasDeVuelo("abc@xyz");
        verify(repositorioReserva).pagarReservasDeVuelo("abc@xyz");
    }

    @Test
    public void obtenerReservasPagadas_delegacionAlRepo() {
        Reserva mockR = new Reserva();
        List<Reserva> stubList = Collections.singletonList(mockR);
        when(repositorioReserva.buscarPorEmailPagadas("e@f"))
                .thenReturn(stubList);

        List<Reserva> result = servicioReserva.obtenerReservasPorEmailPagados("e@f");
        assertSame(stubList, result);
    }
}
