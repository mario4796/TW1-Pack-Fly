package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.Vuelo;
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
    public String vistaBusquedaVuelo() {
        return "busqueda-vuelo"; // muestra la vista con el formulario vacío
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
            model.addAttribute("vueloUrl", true); // esto activa el th:if
            model.addAttribute("valorIda", vuelo.getPrecio() );
            model.addAttribute("valorVuelta", vuelo.getPrecio());
            model.addAttribute("fechaIda", fechaIda);
            model.addAttribute("fechaVuelta", fechaVuelta);
        } else {
            model.addAttribute("error", "Vuelo no encontrado");
        }

        return "busqueda-vuelo";
    }
}
