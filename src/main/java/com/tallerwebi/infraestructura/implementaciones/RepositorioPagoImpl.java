package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.dominio.entidades.Usuario;
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
    public void guardar(Pago pago, Long id) {
        sessionFactory.getCurrentSession().save(pago);
    }

    @Override
    public boolean existePagoPorReserva(Long idReserva) {
        String hql = "SELECT COUNT(p) > 0 FROM Pago p WHERE p.reserva.id = :idReserva";
        return (Boolean) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("idReserva", idReserva)
                .uniqueResult();
    }


}
