package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.presentacion.dtos.HotelDto;

import java.util.List;

public interface ServicioHotel {
    List<HotelDto> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children, String children_ages);
    void reserva(Hotel hotel);
    List<HotelDto> buscarReservas(Long idUsuario);
    void eliminarReserva (Long idUsuario, String nameHotel);
}
