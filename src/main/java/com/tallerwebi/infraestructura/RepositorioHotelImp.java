package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioHotel;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioHotelImp implements RepositorioHotel {

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
    public List<HotelDto> buscarReserva(Long idUsuario) {
        return this.sessionFactory.getCurrentSession()
                .createQuery(
                        "SELECT new com.tallerwebi.presentacion.dtos.HotelDto(h.name, h.ciudad, h.checkIn, h.checkOut, h.adult, h.children) " +
                                "FROM Hotel h WHERE h.usuario.id = :idUsuario", HotelDto.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }



}
