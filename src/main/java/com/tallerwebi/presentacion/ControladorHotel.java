package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.ServicioHotel;
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

    @GetMapping("/buscar-hoteles")
    public String buscar(
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            Model model
    ) {
        List<Hotel> hoteles = hotelService.buscarHoteles(ciudad, checkIn, checkOut);
        model.addAttribute("hoteles", hoteles);
        return "resultado-hoteles"; // Nombre de tu vista Thymeleaf
    }
    @GetMapping("/formulario-hoteles")
    public String mostrarFormulario() {
        return "formulario-hoteles";
    }
}
