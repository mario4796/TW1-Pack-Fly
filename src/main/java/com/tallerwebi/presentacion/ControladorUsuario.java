package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioExcursiones;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        List<Hotel> hotelesPagados = hotelService.buscarHotelesPagados(usuario.getId());
        List<HotelDto> hotelesDtoPagados = hotelService.obtenerHotelesDto(hotelesPagados);
        List<Vuelo> vuelosPagados = servicioReserva.obtenerReservasPorEmailPagados(usuario.getEmail());
        List<Excursion> excursionesPagadas = servicioExcursiones.obtenerExcursionesDeUsuarioPagados(usuario.getId());


        //usuariop.put("historialViajes", historialViajes);

        // Wishlist de destinos de prueba
        List<String> wishlist = Arrays.asList("Tokio", "Sydney", "Toronto");
        usuariop.put("wishlist", wishlist);

        //reserva


        List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
        List<HotelDto> hotelesDto = hotelService.obtenerHotelesDto(hoteles);

        System.out.println("Hoteles pagados encontrados: " + hotelesDtoPagados.size());
        for (HotelDto h : hotelesDtoPagados) {
            System.out.println("Hotel pagado: " + h.getName() + " | Pagado: " + h.getPagado());
        }

        List<Vuelo> vuelos = servicioReserva.obtenerReservasPorEmail(usuario.getEmail());

        List<Excursion> excursiones = servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId());

        ResumenPagoDto resumen = servicioLogin.obtenerDeudaDelUsuario(usuario.getId(), hotelesDto, vuelos, excursiones);
        usuario.setApagar(resumen.getTotal());

        model.addAttribute("vuelos", vuelos);
        model.addAttribute("hoteles", hotelesDto);
        model.addAttribute("excursiones", excursiones);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuariop", usuariop);

        model.addAttribute("hotelesPagados", hotelesDtoPagados);
        model.addAttribute("vuelosPagados", vuelosPagados);
        model.addAttribute("excursionesPagadas", excursionesPagadas);

        return "perfil-usuario";
    }

    @PostMapping("/cambiar-password")
    public String cambiarPassword(@RequestParam String password,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        try {
            servicioLogin.modificarPassword(usuario.getId(), password);
            redirectAttributes.addFlashAttribute("mensaje", "Contraseña modificada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al modificar la contraseña.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

        return "redirect:/perfil-usuario";
    }

    @PostMapping("/cambiar-email")
    public String cambiarEmail(@RequestParam String email,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        try {
            servicioLogin.modificarEmail(usuario.getId(), email);
            redirectAttributes.addFlashAttribute("mensaje", "Email modificado con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al modificar el email.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

        return "redirect:/perfil-usuario";
    }

    @PostMapping("/cambiar-nombreYApellido")
    public String cambiarNombreYApellido(@RequestParam String nombre,
                                         @RequestParam String apellido,
                                         HttpServletRequest request,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        try {
            servicioLogin.modificarNombreYApellido(usuario.getId(), nombre, apellido);
            Usuario actualizado = servicioLogin.buscarUsuarioPorId(usuario.getId());
            request.getSession().setAttribute("USUARIO", actualizado);
            redirectAttributes.addFlashAttribute("mensaje", "Nombre y apellido modificados con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al modificar el nombre y apellido");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

        return "redirect:/perfil-usuario";
    }
}
