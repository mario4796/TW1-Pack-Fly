// src/main/java/com/tallerwebi/presentacion/ControladorExcursion.java
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Excursion;
import com.tallerwebi.dominio.ExcursionDTO;
import com.tallerwebi.dominio.RepositorioExcursion;
import com.tallerwebi.dominio.ServicioExcursiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ControladorExcursion {

    private final ServicioExcursiones servicio;

    @Autowired
    private  RepositorioExcursion repositorioExcursion;

    public ControladorExcursion(ServicioExcursiones servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/excursiones")
    public String verExcursiones(
            @RequestParam(name = "loc",   defaultValue = "Buenos Aires") String loc,
            @RequestParam(name = "query", defaultValue = "excursiones")   String query,
            Model model) {

        List<ExcursionDTO> lista = servicio.getExcursiones(loc, query);
        model.addAttribute("excursiones", lista);

        // Solo el nombre de la plantilla, sin el prefijo Thymeleaf
        return "excursiones";
    }

    @PostMapping("/excursiones/guardar")
    public String guardarExcursion(ExcursionDTO dto) {
        Excursion excursion = dto.toEntity();
        repositorioExcursion.guardar(excursion);
        return "redirect:/excursiones " ;
    }
}
