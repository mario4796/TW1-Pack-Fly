package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.infraestructura.RepositorioHotel;
import com.tallerwebi.dominio.entidades.Hotel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
public class RepositorioHotelImp implements RepositorioHotel {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioHotelImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Boolean reservar(Hotel hotel) {
        try {
            this.sessionFactory.getCurrentSession().save(hotel);
            return true;
        } catch (Exception e) {
            // Loguear el error si es necesario
            return false;
        }
    }

    @Override
    public List<Hotel> buscarReserva(Long idUsuario) {
        return this.sessionFactory.getCurrentSession()
                .createQuery(
                        "SELECT new com.tallerwebi.dominio.entidades.Hotel(h.id, h.name, h.ciudad, h.checkIn, h.checkOut, h.adult, h.children, h.precio) " +
                                "FROM Hotel h WHERE h.usuario.id = :idUsuario", Hotel.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }

    @Override
    public void eliminarReserva(Long idUsuario, String nameHotel) {
        this.sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Hotel h WHERE h.usuario.id = :idUsuario AND h.name = :nameHotel")
                .setParameter("idUsuario", idUsuario)
                .setParameter("nameHotel", nameHotel)
                .executeUpdate();
    }


    @Override
    public Hotel buscarPorUsuarioYNombre(Long idUsuario, Long idHotel) {
        String hql = "FROM Hotel h WHERE h.usuario.id = :idUsuario AND h.id = :idHotel";
        List<Hotel> resultados = sessionFactory.getCurrentSession()
                .createQuery(hql, Hotel.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("idHotel", idHotel)
                .list();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void actualizar(Hotel hotel) {
        sessionFactory.getCurrentSession().update(hotel);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hotel> obtenerTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Hotel", Hotel.class)
                .getResultList();
    }


}
