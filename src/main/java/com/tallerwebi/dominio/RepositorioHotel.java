package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;

import java.util.List;

public interface RepositorioHotel {
    Boolean reservar(Hotel hotel);
    List<Hotel> buscarReserva(Long idUsuario);
    void eliminarReserva(Long idUsuario, String nameHotel);
    Hotel buscarPorUsuarioYNombre(Long usuarioId, Long idHotel);
    void actualizar(Hotel hotel);
}
