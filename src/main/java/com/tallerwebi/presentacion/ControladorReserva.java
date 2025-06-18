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

    @PostMapping("/eliminarReservaExcursion")
    public String eliminarReservaExcursion(@RequestParam String title,
                                           HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        servicioExcursiones.eliminarReserva(usuario.getId(), title);
        return "redirect:/reservas";
    }

    @PostMapping("/editarReservaVuelo")
    public String editarReservaVuelo(
            @RequestParam String email,
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam String fechaIda,
            @RequestParam String fechaVuelta,
            @RequestParam String fechaIdaOriginal,
            @RequestParam String fechaVueltaOriginal
    ) {
        //servicioReserva.editarReserva(email, origen, destino, fechaIda, fechaVuelta, fechaIdaOriginal, fechaVueltaOriginal);
        return "redirect:/reservas";
    }

    @PostMapping("/editarReservaHotel")
    public String editarReservaHotel(
            @RequestParam String name,
            @RequestParam String newName,
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkout,
            @RequestParam Integer adults,
            @RequestParam Integer children,
            HttpServletRequest request
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        hotelService.editarReserva(usuario.getId(), name, newName, ciudad, checkIn, checkout, adults, children);
        return "redirect:/reservas";
    }

    @PostMapping("/editarReservaExcursion")
    public String editarReservaExcursion(
            @RequestParam String titleOriginal,
            @RequestParam String title,
            @RequestParam String url,
            HttpServletRequest request
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        //servicioExcursiones.editarReserva(usuario.getId(), titleOriginal, title, url);
        return "redirect:/reservas";
    }

}
