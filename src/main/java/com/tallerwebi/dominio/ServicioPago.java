package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;

public interface ServicioPago {

    void procesarPago(Reserva reserva, Hotel hotel, Excursion excursion, ResumenPagoDto resumen);
}
