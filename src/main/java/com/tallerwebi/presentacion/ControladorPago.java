package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
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

    @GetMapping("/pago-formulario")
    public String mostrarFormularioPago(@RequestParam Long idReserva, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario == null) {
            return "redirect:/login"; // redirige si la sesión no contiene usuario
        }

        String email = usuario.getEmail();
        Reserva reserva = servicioReserva.buscarPorIdYEmail(email, idReserva);

        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }

        model.addAttribute("reserva", reserva);
        return "pago-formulario";
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
            throw new RuntimeException("Reserva no encontrada");
        }

        Pago pago = servicioPago.procesarPago(reserva, numeroTarjeta, titular);
        model.addAttribute("pago", pago);
        return "pago-exitoso";
    }


}
