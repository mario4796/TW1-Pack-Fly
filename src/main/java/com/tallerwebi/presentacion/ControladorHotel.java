package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Hotel;
import com.tallerwebi.dominio.HotelResponse;
import com.tallerwebi.dominio.ServicioHoteles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hoteles")
public class ControladorHotel {

    @Autowired
    private ServicioHoteles servicioHoteles;

    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicioHoteles", new HotelResponse());
        return "buscar-hoteles";
    }

    @PostMapping("/buscar")
    public String buscarHoteles(@ModelAttribute HotelResponse hotelResponse, Model model) {
        List<Hotel> hoteles = servicioHoteles.getHotels(hotelResponse);
        model.addAttribute("hoteles", hoteles);
        return "resultados-hoteles";
    }
}
