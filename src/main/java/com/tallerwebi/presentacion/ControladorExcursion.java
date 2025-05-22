// src/main/java/com/tallerwebi/presentacion/ControladorExcursion.java
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Excursion;
import com.tallerwebi.dominio.ServicioExcursiones;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ControladorExcursion {

    private final ServicioExcursiones servicio;

    public ControladorExcursion(ServicioExcursiones servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/excursiones")
    public String verExcursiones(
            @RequestParam(name = "loc",   defaultValue = "Buenos Aires") String loc,
            @RequestParam(name = "query", defaultValue = "excursiones")   String query,
            Model model) {

        List<Excursion> lista = servicio.getExcursiones(loc, query);
        model.addAttribute("excursiones", lista);

        // Solo el nombre de la plantilla, sin el prefijo Thymeleaf
        return "excursiones";
    }
}
