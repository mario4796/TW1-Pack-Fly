// src/main/java/com/tallerwebi/presentacion/ControladorExcursion.java
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
            Model model,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuarioLogueado", usuario != null);


        List<ExcursionDTO> lista = servicio.getExcursiones(loc, query);
        model.addAttribute("excursiones", lista);

        // Solo el nombre de la plantilla, sin el prefijo Thymeleaf
        return "excursiones";
    }

    @PostMapping("/excursiones/guardar")
    public String guardarExcursion(ExcursionDTO dto, HttpSession session, RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !usuario.activo()) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para guardar excursiones");
            return "redirect:/login";
        }

        Excursion excursion = dto.toEntity();
        excursion.setUsuario(usuario);
        repositorioExcursion.guardar(excursion);


        return "redirect:/excursiones" ;
    }
}
