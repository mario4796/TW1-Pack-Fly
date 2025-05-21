package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Hotel;
import com.tallerwebi.dominio.HotelResponse;
import com.tallerwebi.dominio.ServicioHoteles;
import com.tallerwebi.dominio.Vuelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/hoteles")
public class ControladorHotel {

    @Autowired
    private ServicioHoteles servicioHoteles;

    @GetMapping("/hoteles")
    public String mostrarHoteles(HttpSession session, Model model) {
        Vuelo vuelo = (Vuelo) session.getAttribute("vuelo");

        if (vuelo == null) {
            model.addAttribute("error", "No hay información de vuelo.");
            return "busqueda-vuelo";
        }

        List<Hotel> hoteles = servicioHoteles.getHotels(
                vuelo.getDestino(), vuelo.getFechaIda(), vuelo.getFechaVuelta()
        );

        model.addAttribute("hoteles", hoteles);
        return "hoteles";
    }

}
