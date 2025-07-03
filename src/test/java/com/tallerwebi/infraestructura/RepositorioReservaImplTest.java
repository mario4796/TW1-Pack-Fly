package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.infraestructura.implementaciones.RepositorioReservaImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositorioReservaImplTest {

    @Mock private SessionFactory sessionFactory;
    @Mock private Session        session;
    @Mock private Query<Reserva> query;

    @InjectMocks private RepositorioReservaImpl repo;

    @Before
    public void setUp() {
        // Cuando pidan la sesión actual, devolvemos nuestro mock
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void guardar_delegarSave() {
        Reserva r = new Reserva();
        repo.guardar(r);
        verify(session).save(r);
    }

    @Test
    public void buscarPorEmail_delegarQueryYParametrizar() {
        List<Reserva> esperada = Collections.singletonList(new Reserva());
        // Mockeamos el createQuery y la cadena de fluido de parámetros
        when(session.createQuery(anyString(), eq(Reserva.class))).thenReturn(query);
        when(query.setParameter(eq("email"), anyString())).thenReturn(query);
        when(query.list()).thenReturn(esperada);

        List<Reserva> resultado = repo.buscarPorEmail("foo@bar");

        assertSame(esperada, resultado);
        verify(session).createQuery("FROM Reserva WHERE email = :email AND pagado = false", Reserva.class);
        verify(query).setParameter("email", "foo@bar");
        verify(query).list();
    }

    @Test
    public void eliminarReserva_delegarCreateQueryYExecuteUpdate() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);

        repo.eliminarReserva("u@x", "2025-07-01", "2025-07-05");

        verify(session).createQuery(
                "DELETE FROM Reserva r WHERE r.email = :email AND r.fechaIda = :fechaIda AND r.fechaVuelta = :fechaVuelta"
        );
        verify(query).setParameter("email", "u@x");
        verify(query).setParameter("fechaIda", "2025-07-01");
        verify(query).setParameter("fechaVuelta", "2025-07-05");
        verify(query).executeUpdate();
    }

    @Test
    public void buscarPorIdyEmail_delegarQueryYList() {
        Reserva esperado = new Reserva();
        when(session.createQuery(anyString(), eq(Reserva.class))).thenReturn(query);
        when(query.setParameter(eq("email"), anyString())).thenReturn(query);
        when(query.setParameter(eq("idVuelo"), anyLong())).thenReturn(query);
        when(query.list()).thenReturn(Arrays.asList(esperado));

        Reserva resultado = repo.buscarPorIdyEmail("e@m", 77L);

        assertSame(esperado, resultado);
    }

    @Test
    public void buscarPorId_viaGet() {
        Reserva esperado = new Reserva();
        when(session.get(Reserva.class, 42L)).thenReturn(esperado);
        assertSame(esperado, repo.buscarPorId(42L));
    }

    @Test
    public void actualizar_delegarUpdate() {
        Reserva r = new Reserva();
        repo.actualizar(r);
        verify(session).update(r);
    }

    @Test
    public void pagarReservasDeVuelo_delegarQueryYExecuteUpdate() {
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(eq("email"), anyString())).thenReturn(query);

        repo.pagarReservasDeVuelo("p@y");

        verify(session).createQuery("UPDATE Reserva SET pagado = true WHERE email = :email");
        verify(query).setParameter("email", "p@y");
        verify(query).executeUpdate();
    }

    @Test
    public void buscarPorEmailPagadas_delegarQueryYList() {
        List<Reserva> esperada = Collections.singletonList(new Reserva());
        when(session.createQuery(anyString(), eq(Reserva.class))).thenReturn(query);
        when(query.setParameter(eq("email"), anyString())).thenReturn(query);
        when(query.list()).thenReturn(esperada);

        List<Reserva> resultado = repo.buscarPorEmailPagadas("p@q");

        assertSame(esperada, resultado);
    }
}
