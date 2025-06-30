package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ServicioHotel hotelService;
    @Autowired
    private ServicioReserva servicioReserva;
    @Autowired
    private ServicioExcursiones  servicioExcursiones;
    @Autowired
    private ServicioLogin servicioLogin;

    @GetMapping("/perfil-usuario")
    public String perfil(HttpServletRequest request, Model model) {
        // Usuario de prueba
        Map<String, Object> usuariop = new HashMap<>();
        usuariop.put("nombre", "Juan Pérez");
        usuariop.put("nivel", "Oro");
        usuariop.put("millas", 12500);
        usuariop.put("viajes", 8);
        usuariop.put("destinos", 5);
        usuariop.put("ranking", 2);

        // Historial de viajes de prueba
        List<Map<String, String>> historialViajes = Arrays.asList(
                Map.of("destino", "Madrid", "fecha", "12/06/2025"),
                Map.of("destino", "Roma", "fecha", "20/04/2025")
        );
        usuariop.put("historialViajes", historialViajes);

        // Wishlist de destinos de prueba
        List<String> wishlist = Arrays.asList("Tokio", "Sydney", "Toronto");
        usuariop.put("wishlist", wishlist);

        //reserva
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
        List<HotelDto> hotelesDto = hotelService.obtenerHotelesDto(hoteles);
        List<Reserva> vuelos = servicioReserva.obtenerReservasPorEmail(usuario.getEmail());

        List<Excursion> excursiones = servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId());

        usuario.setApagar(servicioLogin.obtenerDeudaDelUsuario(hotelesDto, vuelos, excursiones));
        model.addAttribute("vuelos", vuelos);
        model.addAttribute("hoteles", hotelesDto);
        model.addAttribute("excursiones", excursiones);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuariop", usuariop);

        return "perfil-usuario";
    }
}
