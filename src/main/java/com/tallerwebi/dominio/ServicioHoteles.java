package com.tallerwebi.dominio;

import java.util.List;
import com.tallerwebi.dominio.entidades.Hotel;

public interface ServicioHoteles {
    List<Hotel> getHotels(String destino, String checkIn, String checkOut);

}
