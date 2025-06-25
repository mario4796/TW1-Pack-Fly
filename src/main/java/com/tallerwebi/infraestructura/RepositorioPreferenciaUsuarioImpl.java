package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.PreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioPreferenciaUsuarioImpl implements RepositorioPreferenciaUsuario {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PreferenciaUsuario obtenerPorUsuario(Usuario usuario) {
        try {
            return entityManager.createQuery(
                            "FROM PreferenciaUsuario WHERE usuario = :usuario", PreferenciaUsuario.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void guardar(PreferenciaUsuario preferenciaUsuario) {
        entityManager.persist(preferenciaUsuario);
    }

    @Override
    public void actualizar(PreferenciaUsuario preferenciaUsuario) {
        entityManager.merge(preferenciaUsuario);
    }
}
