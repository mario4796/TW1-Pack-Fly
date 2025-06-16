package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.Excursion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorReserva {

    @Autowired
    private ServicioHotel hotelService;

    @Autowired
    private ServicioReserva servicioReserva;

    @Autowired
    private ServicioExcursiones servicioExcursiones;

    @GetMapping("/reservas")
    public String vistaReservas(HttpServletRequest request, Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        List<HotelDto> hoteles = hotelService.buscarReservas(usuario.getId());
        List<Reserva> vuelos = servicioReserva.obtenerReservasPorEmail(usuario.getEmail());

        List<Excursion> excursiones = servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId());

        model.addAttribute("vuelos", vuelos);
        model.addAttribute("hoteles", hoteles);
        model.addAttribute("excursiones", excursiones);

        return "reservas";
    }

    @PostMapping("/eliminarReservaHotel")
    public String eliminarReservaHotel(@RequestParam String name,
                                 HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        hotelService.eliminarReserva(usuario.getId(), name);
        return "redirect:/reservas";
    }

    @PostMapping("/eliminarReservaVuelo")
    public String eliminarReservaVuelo(@RequestParam String email,
                                       @RequestParam String fechaVuelta,
                                       @RequestParam String fechaIda) {

        servicioReserva.eliminarReserva(email, fechaIda, fechaVuelta);
        return "redirect:/reservas";
    }
}
