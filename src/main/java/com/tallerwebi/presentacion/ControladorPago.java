package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPago {

    @Autowired private ServicioReserva servicioReserva;
    @Autowired private ServicioPago servicioPago;
    @Autowired private ServicioHotel servicioHotel;
    @Autowired private ServicioExcursiones servicioExcursiones;

    @GetMapping("/pago-formulario")
    public String mostrarFormularioPago(@RequestParam Long idReserva, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario == null) {
            return "redirect:/login";
        }

        String email = usuario.getEmail();
        Reserva reserva = servicioReserva.buscarPorIdYEmail(email, idReserva);

        if (reserva != null) {
            model.addAttribute("reserva", reserva);
            return "pago-formulario";
        }

        Hotel hotel = servicioHotel.buscarReservaPorIdYUsuario(idReserva, usuario.getId());
        if (hotel != null) {
            model.addAttribute("reserva", hotel);
            return "pago-formulario";
        }

        Excursion excursion = servicioExcursiones.buscarReservaPorIdYUsuario(idReserva, usuario.getId());
        if (excursion != null) {
            model.addAttribute("reserva", excursion);
            return "pago-formulario";
        }

        throw new RuntimeException("Reserva no encontrada");
    }



    @PostMapping("/pagar-reserva")
    public String pagarReserva(@RequestParam Long idReserva,
                               @RequestParam String numeroTarjeta,
                               @RequestParam String titular,
                               HttpSession session,
                               Model model) {
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario == null) {
            return "redirect:/login";
        }

        String email = usuario.getEmail();
        Reserva reserva = servicioReserva.buscarPorIdYEmail(email, idReserva);

        if (reserva == null) {
            Hotel hotel = servicioHotel.buscarReservaPorIdYUsuario(idReserva, usuario.getId());
            if (hotel != null) {
                reserva = new Reserva();
                reserva.setUsuario(usuario);
                reserva.setPrecio(hotel.getPrecio());
            } else {
                Excursion excursion = servicioExcursiones.buscarReservaPorIdYUsuario(idReserva, usuario.getId());
                if (excursion != null) {
                    reserva = new Reserva();
                    reserva.setUsuario(usuario);
                    reserva.setPrecio(excursion.getPrecio());
                } else {
                    throw new RuntimeException("Reserva no encontrada");
                }
            }
        }

        Pago pago = servicioPago.procesarPago(reserva, numeroTarjeta, titular);
        model.addAttribute("pago", pago);
        return "pago-exitoso";
    }

}
