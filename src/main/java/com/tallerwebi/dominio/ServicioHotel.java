package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioHotel {
    List<Hotel> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children, String children_ages);
}
