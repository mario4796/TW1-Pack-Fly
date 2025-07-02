package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.implementaciones.ServicioHotelImpl;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.utils.IconHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorHotel {
    @Autowired
    private ServicioHotel hotelService;
    @Autowired
    private ServicioEmail servicioEmail;

    @Autowired
    private ServicioPreferenciaUsuario servicioPreferenciaUsuario;


    @Autowired private IconHelper iconHelper;

    public ControladorHotel(ServicioHotel servicioHotel, ServicioPreferenciaUsuario servicioPreferenciaUsuario) {
        this.hotelService = servicioHotel;
        this.servicioPreferenciaUsuario = servicioPreferenciaUsuario;
    }

    @GetMapping("/buscar-hoteles")

    public String buscar(
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer adult,
            @RequestParam Integer children,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            HttpServletRequest request,
            Model model
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario); //

        List<HotelDto> hoteles = hotelService.buscarHoteles(ciudad, checkIn, checkOut, adult, children);

        if (precioMin != null && precioMax != null) {
            hoteles = hoteles.stream()
                    .filter(h -> h.getPrecio() >= precioMin && h.getPrecio() <= precioMax)
                    .collect(Collectors.toList());
        }

        model.addAttribute("hoteles", hoteles);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);

        HotelDto datobusqueda = new HotelDto();
        datobusqueda.setCiudad(ciudad);
        datobusqueda.setCheckIn(checkIn);
        datobusqueda.setCheckOut(checkOut);
        datobusqueda.setAdult(adult);
        datobusqueda.setChildren(children);

        model.addAttribute("datobusqueda", datobusqueda);
        model.addAttribute("iconHelper", iconHelper);

        model.addAttribute("ciudad", ciudad);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("adult", adult);
        model.addAttribute("children", children);

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
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) throws MessagingException {

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
        hotel.setPagado(false);


        try {
            hotelService.reserva(hotel);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de hotel creada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al crear la reserva de hotel.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }


        if (usuario != null) {
            int cantidadPersonas = (adult != null ? adult : 0) + (children != null ? children : 0);
            servicioPreferenciaUsuario.registrarReservaHotel(usuario, cantidadPersonas);
        }
        String email = usuario.getEmail();

        /*try {
            servicioEmail.enviarCorreo(
                    email,
                    "Confirmación de Reserva - Pack&Fly",
                    "¡Gracias por tu reserva, " + usuario.getNombre() + "\n"
                            + "Hotel: " + name + " " + ciudad + "\n"
                            + "Fecha entrada: " + checkIn + "\n"
                            + "Fecha salida:" + checkOut + "\n"
                            + "Precio: $" + precio + "\n"
                            + "Recorda que tenes hasta 7 dias antes de la reservacion para pagar, si no su reservacion sera ELIMINADA"

            );
            servicioEmail.enviarCorreo("ordnaelx13@gmail.com", "Nueva Reserva de hotel","El usuario "+email+" ha realizado una reserva con \n"
                    + "Fecha entrada: " + checkIn + "\n"
                    + "Fecha salida:" + checkOut + "\n"
                    + "Precio: $" + precio + "\n");
        } catch (Exception ex) {
            System.err.println("Error al enviar email de reserva de Hotel: " + ex.getMessage());
        }*/


        return "redirect:/busqueda-excursiones";
    }



}
