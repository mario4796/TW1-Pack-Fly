package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioHotel {
    List<Hotel> buscarHoteles(String ciudad, String checkIn, String checkOut);
}
