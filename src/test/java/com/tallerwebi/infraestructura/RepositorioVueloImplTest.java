package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.infraestructura.implementaciones.RepositorioVueloImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositorioVueloImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Vuelo> query;

    @InjectMocks
    private RepositorioVueloImpl repo;

    @Before
    public void setUp() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void guardar_delegarSave() {
        Vuelo vuelo = new Vuelo();

        repo.guardar(vuelo);

        // Verifica que se obtuvo la sesión y se llamó a save()
        verify(sessionFactory).getCurrentSession();
        verify(session).save(vuelo);
    }

    @Test
    public void obtenerTodos_delegarCreateQueryYGetResultList() {
        List<Vuelo> esperados = Arrays.asList(new Vuelo(), new Vuelo());
        when(session.createQuery(eq("FROM Vuelo"), eq(Vuelo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(esperados);

        List<Vuelo> resultados = repo.obtenerTodos();

        assertSame(esperados, resultados);
        verify(sessionFactory).getCurrentSession();
        verify(session).createQuery("FROM Vuelo", Vuelo.class);
        verify(query).getResultList();
    }

    @Test
    public void obtenerTodos_emptyList() {
        when(session.createQuery(eq("FROM Vuelo"), eq(Vuelo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Vuelo> resultados = repo.obtenerTodos();

        assertTrue(resultados.isEmpty());
        verify(query).getResultList();
    }

    @Test
    public void guardar_null_callsSaveWithNull() {
        repo.guardar(null);

        verify(session).save(null);
    }

    @Test
    public void obtenerTodos_invocationOrder() {
        when(session.createQuery(eq("FROM Vuelo"), eq(Vuelo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        repo.obtenerTodos();

        InOrder inOrder = inOrder(sessionFactory, session, query);
        inOrder.verify(sessionFactory).getCurrentSession();
        inOrder.verify(session).createQuery("FROM Vuelo", Vuelo.class);
        inOrder.verify(query).getResultList();
    }
}
