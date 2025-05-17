package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PaqueteServiceImpl implements PaqueteService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Paquete> listarTodos() {
        return em.createQuery("SELECT p FROM Paquete p", Paquete.class).getResultList();
    }

    @Override
    public Paquete buscarPorId(Long id) {
        return em.find(Paquete.class, id);
    }
}
