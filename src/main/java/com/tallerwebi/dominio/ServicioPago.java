package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.Reserva;

public interface ServicioPago {
    Pago procesarPago(Reserva reserva, String numeroTarjeta, String titular);
}