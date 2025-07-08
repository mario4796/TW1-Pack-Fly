package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
import java.util.List;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    void modificarUsuario(Usuario usuario);
    ResumenPagoDto obtenerDeudaDelUsuario(Long id, List<HotelDto> hoteles, List<Vuelo> vuelos, List<Excursion> excursiones);
    void modificarPassword(Long id, String password);
    void modificarEmail(Long id, String email);
    void modificarNombreYApellido(Long id, String nombre, String apellido);
    Usuario buscarUsuarioPorId(Long id);
}
