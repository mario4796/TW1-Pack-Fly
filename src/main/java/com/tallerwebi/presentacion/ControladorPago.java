package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Controlador para el flujo de pago de reservas de vuelo, hotel y/o excursión.
 */
@Controller
public class ControladorPago {

    private final ServicioPago servicioPago;
    private final ServicioReserva servicioReserva;
    private final ServicioHotel servicioHotel;
    private final ServicioExcursiones servicioExcursiones;

    @Autowired
    public ControladorPago(
            ServicioPago servicioPago,
            ServicioReserva servicioReserva,
            ServicioHotel servicioHotel,
            ServicioExcursiones servicioExcursiones) {
        this.servicioPago = servicioPago;
        this.servicioReserva = servicioReserva;
        this.servicioHotel = servicioHotel;
        this.servicioExcursiones = servicioExcursiones;
    }

    /**
     * Muestra el formulario de pago con los detalles de reserva, hotel y/o excursión.
     */
    @GetMapping("/pago-formulario")
    public String mostrarFormularioPago(
        /*    @RequestParam("reservaId") Long reservaId,
            @RequestParam(value = "hotelId", required = false) Long hotelId,
            @RequestParam(value = "excursionId", required = false) Long excursionId,*/
            Model model,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);
    /*
        // Obtener entidades por ID
        Reserva reserva     = servicioReserva.buscarPorId(reservaId);
        Hotel   hotel       = (hotelId != null)     ? servicioHotel.buscarPorId(hotelId)       : null;
        Excursion excursion = (excursionId != null) ? servicioExcursiones.buscarPorId(excursionId) : null;

        model.addAttribute("reserva", reserva);
        model.addAttribute("hotel", hotel);
        model.addAttribute("excursion", excursion);
        model.addAttribute("reservaId", reservaId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("excursionId", excursionId); */
        return "pagoFormulario";
    }

    /**
     * Procesa el pago y redirige de vuelta a la lista de reservas.
     */
    /* @PostMapping("/pagar")
    public String realizarPago(
           @RequestParam("reservaId") Long reservaId,
            @RequestParam(value = "hotelId", required = false) Long hotelId,
            @RequestParam(value = "excursionId", required = false) Long excursionId,
            Model model,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);

       Reserva reserva     = servicioReserva.buscarPorId(reservaId);
        Hotel   hotel       = (hotelId != null)     ? servicioHotel.buscarPorId(hotelId)       : null;
        Excursion excursion = (excursionId != null) ? servicioExcursiones.buscarPorId(excursionId) : null;

        servicioPago.procesarPago(reserva, hotel, excursion);
        return "redirect:/reservas";
    }*/
}
