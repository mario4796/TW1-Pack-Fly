package com.tallerwebi.dominio;

import java.util.Date;
import java.util.List;
import com.tallerwebi.dominio.Hotel;

public interface ServicioHoteles {
    List<Hotel> getHotels(String destino, String checkIn, String checkOut);

}
