package com.tallerwebi.dominio;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositorioExcursion {

    @PersistenceContext
    private EntityManager entityManager;

    public void guardar(Excursion excursion){
        entityManager.persist(excursion);
    }

    public List<Excursion> obtenerTodas(){
        return entityManager.createQuery("FROM Excursion", Excursion.class).getResultList();
    }
}
