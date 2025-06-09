package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioHoteles {
    List<Hotel> getHotels(String destino, String checkIn, String checkOut);

}
