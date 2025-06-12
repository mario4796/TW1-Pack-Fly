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
            @RequestParam Integer adults,
            @RequestParam Integer children,
            @RequestParam String children_ages,
            Model model
    ) {
        List<HotelDto> hoteles = hotelService.buscarHoteles(ciudad, checkIn, checkOut, adults, children, children_ages);
        HotelDto datobusqueda  = new HotelDto();
        datobusqueda.setCiudad(ciudad);
        datobusqueda.setCheckIn(checkIn);
        datobusqueda.setCheckout(checkOut);
        datobusqueda.setAdults(adults);
        datobusqueda.setChildren(children);
        model.addAttribute("datobusqueda", datobusqueda);
        model.addAttribute("hoteles", hoteles);
        model.addAttribute("iconHelper", iconHelper);
        return "busqueda-hoteles"; // Nombre de tu vista Thymeleaf
    }

    @GetMapping("/busqueda-hoteles")
    public String mostrarFormulario() {
        return "busqueda-hoteles";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam String name,
                           @RequestParam String ciudad,
                           @RequestParam String checkIn,
                           @RequestParam String checkOut,
                           @RequestParam Integer adults,
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
        hotel.setAdult(adults);
        hotel.setChildren(children);
        hotel.setUsuario(usuario);
        hotelService.reserva(hotel);

        return "redirect:/excursiones?reservaExitosa=true"; // o donde quieras
    }

    @GetMapping("/reservas")
    public String vistaReservas(HttpServletRequest request,
                                Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        List<HotelDto> hoteles = hotelService.buscarReservas(usuario.getId());
        model.addAttribute("hoteles", hoteles);
        return "reservas"; // muestra la vista con el formulario vacío
    }

}
