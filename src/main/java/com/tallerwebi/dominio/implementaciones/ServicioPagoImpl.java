package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.Reserva;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServicioPagoImpl implements ServicioPago {

    @Override
    public Pago procesarPago(Reserva reserva, String numeroTarjeta, String titular) {
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setNumeroTarjeta(numeroTarjeta);
        pago.setTitular(titular);
        pago.setMontoPagado(reserva.getPrecio());
        pago.setFechaPago(LocalDateTime.now());
        pago.setPagoExitoso(true);
        return pago;
    }
}