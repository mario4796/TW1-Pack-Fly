package com.tallerwebi.dominio;

import java.util.List;
import com.tallerwebi.dominio.Hotel;

public interface ServicioHoteles {
    List<Hotel> getHotels(HotelResponse hotelResponse);

}
