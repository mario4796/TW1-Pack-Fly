package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
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
import java.util.stream.Collectors;

@Controller
public class ControladorHotel {
    @Autowired
    private ServicioHotel hotelService;

    @Autowired
    private ServicioPreferenciaUsuario servicioPreferenciaUsuario;



    @Autowired private IconHelper iconHelper;

    @GetMapping("/buscar-hoteles")

    public String buscar(
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer adult,
            @RequestParam Integer children,
            @RequestParam String children_ages,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            HttpServletRequest request,
            Model model
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario); // ✅ Solución clave

        List<HotelDto> hoteles = hotelService.buscarHoteles(ciudad, checkIn, checkOut, adult, children, children_ages);

        if (precioMin != null && precioMax != null) {
            hoteles = hoteles.stream()
                    .filter(h -> h.getPrecio() >= precioMin && h.getPrecio() <= precioMax)
                    .collect(Collectors.toList());
        }

        model.addAttribute("hoteles", hoteles); // asegúrate que sea "hoteles"
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);

        HotelDto datobusqueda = new HotelDto();
        datobusqueda.setCiudad(ciudad);
        datobusqueda.setCheckIn(checkIn);
        datobusqueda.setCheckOut(checkOut);
        datobusqueda.setAdult(adult);
        datobusqueda.setChildren(children);
        datobusqueda.setChildren_ages(children_ages);

        model.addAttribute("datobusqueda", datobusqueda);

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
                           @RequestParam Double precio,
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
        hotel.setPrecio(precio);
        hotelService.reserva(hotel);


        if (usuario != null) {
            int cantidadPersonas = (adult != null ? adult : 0) + (children != null ? children : 0);
            servicioPreferenciaUsuario.registrarReservaHotel(usuario, cantidadPersonas);
        }


        return "redirect:/excursiones?reservaExitosa=true";
    }



}
