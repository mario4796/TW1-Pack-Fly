package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioVuelo;
import com.tallerwebi.dominio.Vuelo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioVueloImpl implements RepositorioVuelo {

    private final SessionFactory sessionFactory;

    public RepositorioVueloImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Vuelo vuelo) {
        sessionFactory.getCurrentSession().save(vuelo);
    }
}
