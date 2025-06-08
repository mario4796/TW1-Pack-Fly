package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hotel;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.presentacion.utils.IconHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ControladorHotel {
    @Autowired
    private ServicioHotel hotelService;
    @Autowired private IconHelper iconHelper;

    @GetMapping("/buscar-hoteles")
    public String buscar(
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer adults,
            @RequestParam Integer children,
            @RequestParam String children_ages,
            Model model
    ) {
        List<Hotel> hoteles = hotelService.buscarHoteles(ciudad, checkIn, checkOut, adults, children, children_ages);
        model.addAttribute("hoteles", hoteles);
        model.addAttribute("iconHelper", iconHelper);
        return "resultado-hoteles"; // Nombre de tu vista Thymeleaf
    }
    @GetMapping("/formulario-hoteles")
    public String mostrarFormulario() {
        return "formulario-hoteles";
    }
}
