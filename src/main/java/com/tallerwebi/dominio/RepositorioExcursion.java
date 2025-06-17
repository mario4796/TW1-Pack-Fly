package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositorioExcursion {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioExcursion(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public void guardar(Excursion excursion){
        entityManager.persist(excursion);
    }

    public List<Excursion> obtenerTodas(){
        return entityManager.createQuery("FROM Excursion", Excursion.class).getResultList();
    }
//    public List<ExcursionDTO> guardar(Long idUsuario) {
//        return this.sessionFactory.getCurrentSession()
//                .createQuery(
//                        "SELECT new com.tallerwebi.presentacion.dtos.ExcursionDTO(h.title, h.url) " +
//                                "FROM Excursion h WHERE h.usuario.id = :idUsuario", ExcursionDTO.class)
//                .setParameter("idUsuario", idUsuario)
//                .getResultList();
//    }
public List<Excursion> obtenerPorUsuario(Long idUsuario) {
    return entityManager.createQuery(
                    "FROM Excursion e WHERE e.usuario.id = :idUsuario", Excursion.class)
            .setParameter("idUsuario", idUsuario)
            .getResultList();
}


    public void eliminarReserva(Long idUsuario, String title) {
        this.sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Excursion e WHERE e.usuario.id = :idUsuario AND e.title = :title")
                .setParameter("idUsuario", idUsuario)
                .setParameter("title", title)
                .executeUpdate();
    }
}
