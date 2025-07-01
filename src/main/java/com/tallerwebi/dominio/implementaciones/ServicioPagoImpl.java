package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.ReservaPagada;
import com.tallerwebi.infraestructura.RepositorioReservaPagada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Service("servicioPago")
@Transactional
public class ServicioPagoImpl implements ServicioPago {

    private final RepositorioReservaPagada repositorioReservaPagada;

    @Autowired
    public ServicioPagoImpl(RepositorioReservaPagada repositorioReservaPagada) {
        this.repositorioReservaPagada = repositorioReservaPagada;
    }

    @Override
    public void procesarPago(Reserva reserva, Hotel hotel, Excursion excursion) {
        LocalDateTime ahora = LocalDateTime.now();
        ReservaPagada pago;

        boolean v = reserva != null;
        boolean h = hotel != null;
        boolean e = excursion != null;

        if (v && !h && !e) {
            pago = ReservaPagada.ofSoloVuelo(reserva, ahora);
        } else if (!v && h && !e) {
            pago = ReservaPagada.ofSoloHotel(reserva, hotel, ahora);
        } else if (!v && !h && e) {
            pago = ReservaPagada.ofSoloExcursion(reserva, excursion, ahora);
        } else if (v && h && !e) {
            pago = ReservaPagada.ofVueloYHotel(reserva, hotel, ahora);
        } else if (v && !h && e) {
            pago = ReservaPagada.ofVueloYExcursion(reserva, excursion, ahora);
        } else if (!v && h && e) {
            pago = ReservaPagada.ofHotelYExcursion(reserva, hotel, excursion, ahora);
        } else if (v && h && e) {
            pago = ReservaPagada.ofHotelYExcursion(reserva, hotel, excursion, ahora);
        } else {
            throw new IllegalArgumentException(
                    "Debe especificar al menos una reserva, hotel o excursión para procesar el pago.");
        }

        repositorioReservaPagada.guardar(pago);
    }
}