package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorUsuario {

    @GetMapping("/perfil-usuario")
    public String perfil(Model model) {
        // Usuario de prueba
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", "Juan Pérez");
        usuario.put("nivel", "Oro");
        usuario.put("millas", 12500);
        usuario.put("viajes", 8);
        usuario.put("destinos", 5);
        usuario.put("ranking", 2);

        // Historial de viajes de prueba
        List<Map<String, String>> historialViajes = Arrays.asList(
                Map.of("destino", "Madrid", "fecha", "12/06/2025"),
                Map.of("destino", "Roma", "fecha", "20/04/2025")
        );
        usuario.put("historialViajes", historialViajes);

        // Wishlist de destinos de prueba
        List<String> wishlist = Arrays.asList("Tokio", "Sydney", "Toronto");
        usuario.put("wishlist", wishlist);

        model.addAttribute("usuario", usuario);

        return "perfil-usuario";
    }
}
