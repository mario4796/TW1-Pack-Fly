package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ControladorReserva {

    private ServicioReserva servicioReserva;

    @Autowired
    public ControladorReserva(ServicioReserva servicioReserva) {
        this.servicioReserva = servicioReserva;
    }

    @GetMapping("/formulario-reserva")
    public String mostrarFormularioVacio() {
        return "formularioReserva";
    }

    @PostMapping("/formulario-reserva")
    public String mostrarFormularioReserva(@RequestParam String origen,
                                           @RequestParam String destino,
                                           @RequestParam String fechaIda,
                                           @RequestParam String fechaVuelta,
                                           Model model) {
        model.addAttribute("origen", origen);
        model.addAttribute("destino", destino);
        model.addAttribute("fechaIda", fechaIda);
        model.addAttribute("fechaVuelta", fechaVuelta);
        return "formularioReserva";
    }

    @PostMapping("/guardar-reserva")
    public String guardarReserva(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaIda") String fechaIda,
            @RequestParam("fechaVuelta") String fechaVuelta,
            Model model
    ) {
        Reserva reserva = new Reserva(nombre, email, origen, destino, fechaIda, fechaVuelta);
        servicioReserva.guardarReserva(reserva);
        return "reservaExitosa";
    }

    @GetMapping("/ver-reservas")
    public String verReservas(@RequestParam("email") String email, Model model) {
        List<Reserva> reservas = servicioReserva.obtenerReservasPorEmail(email);
        model.addAttribute("reservas", reservas);
        model.addAttribute("email", email);
        return "verReservas";
    }

}

