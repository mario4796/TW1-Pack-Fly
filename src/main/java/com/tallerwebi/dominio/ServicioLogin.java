package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dtos.HotelDto;

import java.util.List;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    void modificarUsuario(Usuario usuario);
    Double obtenerDeudaDelUsuario(List<HotelDto> hoteles,  List<Reserva> vuelos,  List<Excursion> excursiones);

}
