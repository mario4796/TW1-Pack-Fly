package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;

import java.util.List;

public interface ServicioHotel {
    List<HotelDto> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children);
    void reserva(Hotel hotel);
    List<Hotel> buscarReservas(Long idUsuario);
    void eliminarReserva (Long idUsuario, String nameHotel);

    void editarReserva(Long idHotel, Long idUsuario, String name, String ciudad, String checkIn, String checkout, Integer adults, Integer children);

    List<HotelDto> obtenerHotelesDto(List<Hotel> hoteles);

    void pagarHotelesDto(List<Hotel> hoteles);

    Hotel buscarPorId(Long id);

    List<Hotel> buscarHotelesPagados(Long id);


}
