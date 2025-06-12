package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.Vuelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Controller
public class ControladorVuelos {
    @Autowired
    private ServicioVuelos servicioVuelos;

    @Autowired
    private ServicioReserva servicioReserva;

    @Autowired
    public ControladorVuelos(ServicioVuelos servicioVuelos) {
        this.servicioVuelos = servicioVuelos;
    }

    public ControladorVuelos(ServicioReserva servicioReservas) {
        this.servicioReserva = servicioReservas;
    }

    @GetMapping("/busqueda-vuelo")
    public String vistaBusquedaVuelo() {
        return "busqueda-vuelo";
    }

    @PostMapping("/busqueda-vuelo")
    public String buscarVuelo(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaIda,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaVuelta,
            Model model) {

        Vuelo vuelo = servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta);

        if (vuelo != null) {
            model.addAttribute("vuelo", vuelo);
            model.addAttribute("vueloUrl", true);
            model.addAttribute("valorIda", vuelo.getPrecio() );
            model.addAttribute("valorVuelta", vuelo.getPrecio());
            // model.addAttribute("fechaIda", fechaIda);
            // model.addAttribute("fechaVuelta", fechaVuelta);
        } else {
            model.addAttribute("error", "Vuelo no encontrado");
        }

        return "busqueda-vuelo";
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
        return "redirect:/busqueda-hoteles?reservaExitosa=true";
    }


    /*@GetMapping("/ver-reservas")
    public String verReservas(@RequestParam("email") String email, Model model) {
        List<Reserva> reservas = servicioReserva.obtenerReservasPorEmail(email);
        model.addAttribute("reservas", reservas);
        model.addAttribute("email", email);
        return "verReservas";
    }*/


}
