package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.config.PdfGenerator;
import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.ReservaPagada;
import com.tallerwebi.infraestructura.RepositorioReservaPagada;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
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
    @Autowired
    private ServicioEmail servicioEmail;



    @Override
    public void procesarPago(Vuelo vuelo, Hotel hotel, Excursion excursion) {
        LocalDateTime ahora = LocalDateTime.now();
        ReservaPagada pago;

        boolean v = vuelo != null;
        boolean h = hotel != null;
        boolean e = excursion != null;

        if (v && !h && !e) {
            pago = ReservaPagada.ofSoloVuelo(vuelo, ahora);
        } else if (!v && h && !e) {
            pago = ReservaPagada.ofSoloHotel(vuelo, hotel, ahora);
        } else if (!v && !h && e) {
            pago = ReservaPagada.ofSoloExcursion(vuelo, excursion, ahora);
        } else if (v && h && !e) {
            pago = ReservaPagada.ofVueloYHotel(vuelo, hotel, ahora);
        } else if (v && !h && e) {
            pago = ReservaPagada.ofVueloYExcursion(vuelo, excursion, ahora);
        } else if (!v && h && e) {
            pago = ReservaPagada.ofHotelYExcursion(vuelo, hotel, excursion, ahora);
        } else if (v && h && e) {
            pago = ReservaPagada.ofHotelYExcursion(vuelo, hotel, excursion, ahora);
        } else {
            throw new IllegalArgumentException(
                    "Debe especificar al menos una reserva, hotel o excursión para procesar el pago.");
        }

        repositorioReservaPagada.guardar(pago);
        // 👉 Generar y enviar comprobante por correo

    }

}