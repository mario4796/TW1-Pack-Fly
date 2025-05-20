package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.VueloImpl;
import com.tallerwebi.dominio.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VueloController {

    @Autowired
    private VueloService vueloService;

    @GetMapping("/vuelos")
    public String mostrarVuelos(Model model) {
        // Para prueba: agregar vuelos si está vacío
        if (vueloService.obtenerTodos().isEmpty()) {
            vueloService.agregarVuelo(new VueloImpl("2025-06-01", "2025-06-10", "Buenos Aires", "Madrid", 800));
            vueloService.agregarVuelo(new VueloImpl("2025-07-15", "2025-07-25", "Córdoba", "Roma", 950));
        }

        model.addAttribute("vuelos", vueloService.obtenerTodos());
        return "vuelos"; // archivo vuelos.html en templates
    }
}
