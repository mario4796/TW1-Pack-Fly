// src/main/java/com/tallerwebi/presentacion/ControladorExcursion.java
// src/main/java/com/tallerwebi/presentacion/ControladorExcursion.java
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.Excursion; // Asegúrate de importar Excursion
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ControladorExcursion {

    private final ServicioExcursiones servicio; //

    // @Autowired
    // private  RepositorioExcursion repositorioExcursion;

    @Autowired // Puedes mantener este constructor si ya lo tienes
    public ControladorExcursion(ServicioExcursiones servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/excursiones")
    public String verExcursiones(
            @RequestParam(name = "loc",   defaultValue = "") String loc,
            @RequestParam(name = "query", defaultValue = "")   String query,
            Model model,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        List<ExcursionDTO> lista = new ArrayList<>();

        if (!loc.isBlank()) {
            lista = servicio.getExcursiones(loc, query);

            for (ExcursionDTO excursion : lista) {
                // Lógica para asignar precio
                double precio = generarPrecioAleatorio(200, 10000);
                excursion.setPrecio(precio);
            }

        }


        model.addAttribute("excursiones", lista);
        model.addAttribute("usuario", usuario);

        return "excursiones";
    }



    @PostMapping("/excursiones/guardar")
    public String guardarExcursion(ExcursionDTO dto, HttpSession session, RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) session.getAttribute("USUARIO");

        if (usuario == null || !usuario.activo()) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para guardar excursiones");
            return "redirect:/login";
        }

        Excursion excursion = dto.toEntity();
        excursion.setUsuario(usuario);

        try {
            servicio.guardarExcursion(excursion);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de excursion realizada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al reservar el hotel.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

        return "redirect:/reservas" ;
    }


    private double generarPrecioAleatorio(int min, int max) {

        Random random = new Random();
        double precioBase = min + (max - min) * random.nextDouble();
        return Math.round(precioBase /  50) * 50;
    }

//    @GetMapping("/reservas-excursiones")
//    public String verReservasUsuario(HttpSession session, Model model) {
//        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
//
//        if (usuario == null) {
//            return "redirect:/login";
//        }
//
//        Long idUsuario = usuario.getId();
//        List<Excursion> excursiones = servicio.obtenerExcursionesDeUsuario(idUsuario);
//
//        model.addAttribute("excursiones", excursiones);
//        return "reservas"; // Asegurate de que esta vista exista y renderice las excursiones
//    }

}
