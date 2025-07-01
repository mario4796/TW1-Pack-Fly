package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.infraestructura.RepositorioReserva;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public List<Reserva> buscarPorEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Reserva WHERE email = :email", Reserva.class)
                .setParameter("email", email)
                .list();
    }

    @Override
    public void eliminarReserva(String email, String fechaIda, String fechaVuelta) {
        this.sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Reserva r WHERE r.email = :email AND r.fechaIda = :fechaIda AND r.fechaVuelta = :fechaVuelta")
                .setParameter("email", email)
                .setParameter("fechaIda", fechaIda)
                .setParameter("fechaVuelta", fechaVuelta)
                .executeUpdate();
    }

    @Override
    public Reserva buscarPorIdyEmail(String email, Long idVuelo) {
        String hql = "FROM Reserva r WHERE r.email = :email AND r.id = :idVuelo";
        List<Reserva> resultados = sessionFactory.getCurrentSession()
                .createQuery(hql, Reserva.class)
                .setParameter("email", email)
                .setParameter("idVuelo", idVuelo)
                .list();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void actualizar(Reserva reserva)  {
        sessionFactory.getCurrentSession().update(reserva);
    }

    @Override
    public Reserva buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Reserva.class, id);
    }

}
