package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorExcursion {

    private final ServicioExcursiones servicioExcursiones;

    @Autowired
    public ControladorExcursion(ServicioExcursiones servicioExcursiones) {
        this.servicioExcursiones = servicioExcursiones;

    }

    @GetMapping("/excursiones")
    public String verExcursiones(
            @RequestParam(name = "loc", defaultValue = "") String loc,
            @RequestParam(name = "query", defaultValue = "") String query,
            @RequestParam(name = "precioMin", required = false) Double precioMin,
            @RequestParam(name = "precioMax", required = false) Double precioMax,
            Model model,
            HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario); //
        List<ExcursionDTO> lista = servicioExcursiones.getExcursiones(loc, query);

        for (ExcursionDTO excursion : lista) {
            double precio = generarPrecioAleatorio(200, 10000);
            excursion.setPrecio(precio);
        }

        if (precioMin != null || precioMax != null) {
            lista = lista.stream()
                    .filter(e -> (precioMin == null || e.getPrecio() >= precioMin) &&
                            (precioMax == null || e.getPrecio() <= precioMax))
                    .collect(Collectors.toList());
        }

        model.addAttribute("excursiones", lista);

        return "excursiones";
    }

    private double generarPrecioAleatorio(int min, int max) {
        return Math.round((min + Math.random() * (max - min)) / 100) * 100;
    }

    @PostMapping("/excursiones/guardar")
    public String guardarExcursion(ExcursionDTO dto, HttpSession session, RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario == null || !usuario.activo()) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para guardar excursiones");
            return "redirect:/login";
        }
        try {
            Excursion excursion = dto.toEntity();
            excursion.setUsuario(usuario);

            servicioExcursiones.guardarExcursion(excursion);
            redirectAttributes.addFlashAttribute("mensaje", "¡Excursión guardada con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo guardar la excursión: " + e.getMessage());
        }
        return "redirect:/reservas" ;
    }

}
