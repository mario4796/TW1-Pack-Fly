package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            HttpSession session) {

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
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "excursiones";
    }

    private double generarPrecioAleatorio(int min, int max) {
        return Math.round((min + Math.random() * (max - min)) / 100) * 100;
    }
}
