package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void modificarUsuario(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public Double obtenerDeudaDelUsuario(List<HotelDto> hoteles, List<Reserva> vuelos, List<Excursion> excursiones) {
        double deuda = 0.0;

        if (hoteles != null) {
            for (HotelDto hotel : hoteles) {
                deuda += hotel.getPrecio();
            }
        }

        if (vuelos != null) {
            for (Reserva vuelo : vuelos) {
                deuda += vuelo.getPrecio();
            }
        }

        if (excursiones != null) {
            for (Excursion excursion : excursiones) {
                if (excursion != null && excursion.getPrecio() != null) {
                    deuda += excursion.getPrecio();
                }
            }
        }


        return deuda;
    }

}

