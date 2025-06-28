package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.infraestructura.RepositorioPago;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
public class ServicioPagoImpl implements ServicioPago {

    private final RepositorioPago repositorioPago;

    @Autowired
    public ServicioPagoImpl(RepositorioPago repositorioPago) {
        this.repositorioPago = repositorioPago;
    }

    @Override
    public Pago procesarPago(Reserva reserva, String numeroTarjeta, String titular) {
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setNumeroTarjeta(numeroTarjeta);
        pago.setTitular(titular);
        pago.setMontoPagado(reserva.getPrecio());
        pago.setFechaPago(LocalDateTime.now());
        pago.setPagoExitoso(true);

        repositorioPago.guardar(pago);

        return pago;
    }
    @Override
    public boolean estaPagada(Long idReserva) {
        return repositorioPago.existePagoPorReserva(idReserva);
    }

}
