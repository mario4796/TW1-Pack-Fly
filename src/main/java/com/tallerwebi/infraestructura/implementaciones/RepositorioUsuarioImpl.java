package com.tallerwebi.infraestructura.implementaciones;

import com.tallerwebi.dominio.entidades.PreferenciaViaje;
import com.tallerwebi.infraestructura.RepositorioUsuario;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.PreferenciaViajeDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Usuario.class, id);
    }

    @Override
    public void guardar(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public PreferenciaViaje obtenerPreferenciaViaje(Long usuarioId) {
        Session session = sessionFactory.getCurrentSession();

        return (PreferenciaViaje) session
                .createQuery("FROM PreferenciaViaje WHERE usuario.id = :usuarioId")
                .setParameter("usuarioId", usuarioId)
                .uniqueResult();
    }

    @Override
    public void guardarPreferenciaViaje(PreferenciaViajeDTO preferencias) {
        sessionFactory.getCurrentSession().saveOrUpdate(preferencias);
    }
}