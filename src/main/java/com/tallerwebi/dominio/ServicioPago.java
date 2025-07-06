package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Excursion;

public interface ServicioPago {

    void procesarPago(Vuelo vuelo, Hotel hotel, Excursion excursion);
}
