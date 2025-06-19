package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.utils.IconHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorHotel {
    @Autowired
    private ServicioHotel hotelService;

    @Autowired private IconHelper iconHelper;

    @GetMapping("/buscar-hoteles")
    public String buscar(
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer adult,
            @RequestParam Integer children,
            @RequestParam String children_ages,
            HttpServletRequest request,
            Model model
    ) {
        List<HotelDto> hoteles = hotelService.buscarHoteles(ciudad, checkIn, checkOut, adult, children, children_ages);
        HotelDto datobusqueda  = new HotelDto();
        datobusqueda.setCiudad(ciudad);
        datobusqueda.setCheckIn(checkIn);
        datobusqueda.setCheckOut(checkOut);
        datobusqueda.setAdult(adult);
        datobusqueda.setChildren(children);
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);
        model.addAttribute("datobusqueda", datobusqueda);
        model.addAttribute("hoteles", hoteles);
        model.addAttribute("iconHelper", iconHelper);
        return "busqueda-hoteles";
    }

    @GetMapping("/busqueda-hoteles")
    public String mostrarFormulario(HttpServletRequest request,
                                    Model model) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);
        return "busqueda-hoteles";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam String name,
                           @RequestParam String ciudad,
                           @RequestParam String checkIn,
                           @RequestParam String checkOut,
                           @RequestParam Integer adult,
                           @RequestParam Integer children,
                           HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
       // System.out.println("Usuario de la sesión: " + usuario);
        if (usuario == null) {
            return "home";
        }

        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setCiudad(ciudad);
        hotel.setCheckIn(checkIn);
        hotel.setCheckOut(checkOut);
        hotel.setAdult(adult);
        hotel.setChildren(children);
        hotel.setUsuario(usuario);
        hotelService.reserva(hotel);

        return "redirect:/excursiones?reservaExitosa=true";
    }



}
