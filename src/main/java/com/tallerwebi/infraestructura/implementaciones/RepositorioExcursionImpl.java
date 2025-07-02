package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.infraestructura.RepositorioExcursion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositorioExcursionImpl implements RepositorioExcursion {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioExcursionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void guardar(Excursion excursion){
        entityManager.persist(excursion);
    }

    @Override
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
@Override
public List<Excursion> obtenerPorUsuario(Long idUsuario) {
    return entityManager.createQuery(
                    "FROM Excursion e WHERE e.usuario.id = :idUsuario AND e.pagado = false", Excursion.class)
            .setParameter("idUsuario", idUsuario)
            .getResultList();
}



    @Override
    public void eliminarReserva(Long idUsuario, String title) {
        this.sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Excursion e WHERE e.usuario.id = :idUsuario AND e.title = :title")
                .setParameter("idUsuario", idUsuario)
                .setParameter("title", title)
                .executeUpdate();
    }

    @Override
    public Excursion buscarPorUsuarioYExcursion(Long idUsuario, Long idExcursion) {
        String hql = "FROM Excursion e WHERE e.usuario.id = :idUsuario AND e.id = :idExcursion";
        List<Excursion> resultados = sessionFactory.getCurrentSession()
                .createQuery(hql, Excursion.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("idExcursion", idExcursion)
                .list();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void actualizar(Excursion excursion) {
        sessionFactory.getCurrentSession().update(excursion);
    }

    @Override
    public Excursion buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Excursion.class, id);
    }

    @Override
    public void pagarExcursiones(Long id) {
        sessionFactory.getCurrentSession()
                .createQuery("UPDATE Excursion e SET e.pagado = true WHERE e.usuario.id = :id AND e.pagado = false")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Excursion> obtenerPorUsuarioPagado(Long id) {
        return entityManager.createQuery(
                        "FROM Excursion e WHERE e.usuario.id = :idUsuario AND e.pagado = true", Excursion.class)
                .setParameter("idUsuario", id)
                .getResultList();
    }


}



