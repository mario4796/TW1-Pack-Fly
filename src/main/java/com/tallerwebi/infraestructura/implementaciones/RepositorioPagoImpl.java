package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.infraestructura.RepositorioPago;
import com.tallerwebi.dominio.entidades.Pago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPagoImpl implements RepositorioPago {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
    }
}
