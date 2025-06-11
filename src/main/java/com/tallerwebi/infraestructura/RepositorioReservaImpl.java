package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.RepositorioReserva;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("repositorioReserva")
@Transactional
public class RepositorioReservaImpl implements RepositorioReserva {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioReservaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
    }
}
