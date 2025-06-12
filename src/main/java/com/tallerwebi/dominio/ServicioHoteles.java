package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Hotel;

import java.util.List;

public interface ServicioHoteles {
    List<Hotel> getHotels(String destino, String checkIn, String checkOut);

}
