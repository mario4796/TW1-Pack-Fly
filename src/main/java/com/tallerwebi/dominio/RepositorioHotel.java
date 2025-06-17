package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;

import java.util.List;

public interface RepositorioHotel {
    Boolean reservar(Hotel hotel);
    List<HotelDto> buscarReserva(Long idUsuario);
    void eliminarReserva(Long idUsuario, String nameHotel);
}
