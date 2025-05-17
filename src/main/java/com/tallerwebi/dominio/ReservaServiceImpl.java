package com.tallerwebi.dominio;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardar(Reserva reserva) {
        em.persist(reserva);
    }
}
