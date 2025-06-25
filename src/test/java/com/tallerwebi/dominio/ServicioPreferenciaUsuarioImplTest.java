package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioPreferenciaUsuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ServicioPreferenciaUsuarioImplTest {

    @Mock
    private ServicioReserva servicioReservaMock;

    @Mock
    private RepositorioPreferenciaUsuario repositorioPreferenciaMock;

    @InjectMocks
    private ServicioPreferenciaUsuarioImpl servicio;

    @Test
    public void queCreePreferenciaSiNoExisteYLaGuarde() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@ejemplo.com");

        when(repositorioPreferenciaMock.obtenerPorUsuario(usuario)).thenReturn(null);
        when(servicioReservaMock.contarReservasUltimosDias("test@ejemplo.com", 14)).thenReturn(0L);

        servicio.registrarReservaVuelo(usuario, 2, 1500);

        verify(repositorioPreferenciaMock).guardar(any(PreferenciaUsuario.class));
        verify(repositorioPreferenciaMock).actualizar(any(PreferenciaUsuario.class));
    }

    @Test
    public void queActualicePreferenciaSiYaExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@ejemplo.com");

        PreferenciaUsuario preferenciaExistente = new PreferenciaUsuario();
        preferenciaExistente.setUsuario(usuario);
        preferenciaExistente.setMillasAcumuladas(1000);

        when(repositorioPreferenciaMock.obtenerPorUsuario(usuario)).thenReturn(preferenciaExistente);
        when(servicioReservaMock.contarReservasUltimosDias("test@ejemplo.com", 14)).thenReturn(2L);

        servicio.registrarReservaVuelo(usuario, 1, 500);

        assertEquals("Solitario", preferenciaExistente.getTipoViajeVuelo());
        assertEquals(Integer.valueOf(1500), preferenciaExistente.getMillasAcumuladas());
        assertTrue(preferenciaExistente.getViajeroFrecuente());

        verify(repositorioPreferenciaMock).actualizar(preferenciaExistente);
    }
}
