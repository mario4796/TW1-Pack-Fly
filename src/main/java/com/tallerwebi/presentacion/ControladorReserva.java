package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class ControladorReserva {

    private ServicioReserva servicioReserva;

    @Autowired
    public ControladorReserva(ServicioReserva servicioReserva) {
        this.servicioReserva = servicioReserva;
    }

    @PostMapping("/formulario-reserva")
    public String mostrarFormularioReserva(@RequestParam String origen,
                                           @RequestParam String destino,
                                           @RequestParam String fechaSalida,
                                           @RequestParam String horaSalida,
                                           @RequestParam String aerolinea,
                                           Model model) {
        model.addAttribute("origen", origen);
        model.addAttribute("destino", destino);
        model.addAttribute("fechaSalida", fechaSalida);
        model.addAttribute("horaSalida", horaSalida);
        model.addAttribute("aerolinea", aerolinea);
        return "formularioReserva";
    }


    @PostMapping("/guardar-reserva")
    public String guardarReserva(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaSalida") String fechaSalida,
            @RequestParam("horaSalida") String horaSalida,
            @RequestParam("aerolinea") String aerolinea,
            Model model
    ) {
        Reserva reserva = new Reserva(nombre, email, origen, destino, fechaSalida, horaSalida, aerolinea);
        servicioReserva.guardarReserva(reserva);
        return "reservaExitosa";
    }
}
