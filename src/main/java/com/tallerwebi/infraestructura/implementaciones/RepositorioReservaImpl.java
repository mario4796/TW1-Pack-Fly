package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.dominio.entidades.Vuelo;
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
    public void guardar(Vuelo vuelo) {
        sessionFactory.getCurrentSession().save(vuelo);
    }

    @Override
    public List<Vuelo> buscarPorEmail(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT DISTINCT v FROM Vuelo v LEFT JOIN FETCH v.layovers WHERE v.email = :email AND v.pagado = false", Vuelo.class)
                .setParameter("email", email)
                .list();
    }

    @Override
    public List<Vuelo> obtenerTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Vuelo", Vuelo.class)
                .getResultList();
    }

    @Override
    public void eliminarReserva(String email, String fechaIda, String fechaVuelta) {
        List<Vuelo> vuelos = sessionFactory.getCurrentSession()
                .createQuery("FROM Vuelo v WHERE v.email = :email AND v.fechaIda = :fechaIda AND v.fechaVuelta = :fechaVuelta", Vuelo.class)
                .setParameter("email", email)
                .setParameter("fechaIda", fechaIda)
                .setParameter("fechaVuelta", fechaVuelta)
                .getResultList();

        for (Vuelo vuelo : vuelos) {
            sessionFactory.getCurrentSession().remove(vuelo);
        }
    }

    @Override
    public void eliminarReservaSinFechaVuelta(String email, String fechaIda) {
        List<Vuelo> vuelos = sessionFactory.getCurrentSession()
                .createQuery("FROM Vuelo v WHERE v.email = :email AND v.fechaIda = :fechaIda AND (v.fechaVuelta IS NULL OR v.fechaVuelta = '')", Vuelo.class)
                .setParameter("email", email)
                .setParameter("fechaIda", fechaIda)
                .getResultList();

        for (Vuelo vuelo : vuelos) {
            sessionFactory.getCurrentSession().remove(vuelo);
        }
    }

    @Override
    public Vuelo buscarPorIdyEmail(String email, Long idVuelo) {
        String hql = "FROM Vuelo r WHERE r.email = :email AND r.id = :idVuelo";
        List<Vuelo> resultados = sessionFactory.getCurrentSession()
                .createQuery(hql, Vuelo.class)
                .setParameter("email", email)
                .setParameter("idVuelo", idVuelo)
                .list();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void actualizar(Vuelo vuelo)  {
        sessionFactory.getCurrentSession().update(vuelo);
    }

    @Override
    public Vuelo buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Vuelo.class, id);
    }

    @Override
    public void pagarReservasDeVuelo(String email) {
        sessionFactory.getCurrentSession()
                .createQuery("UPDATE Vuelo SET pagado = true WHERE email = :email")
                .setParameter("email", email)
                .executeUpdate();
    }

    @Override
    public List<Vuelo> buscarPorEmailPagadas(String email) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT DISTINCT v FROM Vuelo v LEFT JOIN FETCH v.layovers WHERE v.email = :email AND v.pagado = true", Vuelo.class)
                .setParameter("email", email)
                .list();
    }
}