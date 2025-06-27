package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.infraestructura.implementaciones.RepositorioReservaImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class RepositorioReservaImplTest {

    private RepositorioReservaImpl repositorio;
    private SessionFactory sessionFactory;
    private Session session;

    @Before
    public void setUp() {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        repositorio = new RepositorioReservaImpl(sessionFactory);
    }

    @Test
    public void queSeGuardeReservaConSessionFactory() {
        Reserva reserva = new Reserva();
        repositorio.guardar(reserva);
        verify(session).save(reserva);
    }

    @Test
    public void queSeObtenganReservasPorEmail() {
        String email = "test@email.com";
        List<Reserva> reservasMock = mock(List.class);

        Query<Reserva> query = mock(Query.class);
        when(session.createQuery("FROM Reserva WHERE email = :email", Reserva.class)).thenReturn(query);
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.list()).thenReturn(reservasMock);

        repositorio.buscarPorEmail(email);

        verify(session).createQuery("FROM Reserva WHERE email = :email", Reserva.class);
        verify(query).setParameter("email", email);
        verify(query).list();
    }
}