package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.dominio.entidades.ReservaPagada;
import com.tallerwebi.infraestructura.RepositorioReservaPagada;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository("repositorioReservaPagada")
@Transactional
public class RepositorioReservaPagadaImpl implements RepositorioReservaPagada {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioReservaPagadaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ReservaPagada reservaPagada) {
        sessionFactory.getCurrentSession().save(reservaPagada);
    }
}