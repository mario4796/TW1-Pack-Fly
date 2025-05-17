package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioVuelos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Controller
public class ControladorVuelos {
    private final ServicioVuelos servicioVuelos;
    public ControladorVuelos(ServicioVuelos servicioVuelos) {
        this.servicioVuelos = servicioVuelos;
    }

    @GetMapping("/busqueda-vuelo")
    public String mostrarHome() {
        return "busqueda-vuelo"; // muestra la vista con el formulario vacío
    }

    @PostMapping("/busqueda-vuelo")
    public String buscarVuelo(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaIda,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaVuelta,
            Model model) {
        String vueloUrl = servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta);
        model.addAttribute("vueloEncontrado", true);
        return "busqueda-vuelo";
    }
}
