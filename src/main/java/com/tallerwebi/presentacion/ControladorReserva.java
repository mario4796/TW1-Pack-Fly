package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.presentacion.dtos.ResumenPagoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorReserva {

    @Autowired
    private ServicioHotel hotelService;

    @Autowired
    private ServicioReserva servicioReserva;

    @Autowired
    private ServicioExcursiones servicioExcursiones;

    @Autowired
    private ServicioLogin servicioLogin;

    @Autowired
    private ServicioEmail servicioEmail;

    @GetMapping("/reservas")
    public String vistaReservas(HttpServletRequest request, Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        if (usuario != null) {
            List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
            List<HotelDto> hotelesDto = hotelService.obtenerHotelesDto(hoteles);
            List<Reserva> vuelos = servicioReserva.obtenerReservasPorEmail(usuario.getEmail());
            List<Excursion> excursiones = servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId());

            ResumenPagoDto resumen = servicioLogin.obtenerDeudaDelUsuario(usuario.getId(), hotelesDto, vuelos, excursiones);
            usuario.setApagar(resumen.getTotal());

            model.addAttribute("vuelos", vuelos);
            model.addAttribute("hoteles", hotelesDto);
            model.addAttribute("excursiones", excursiones);
            model.addAttribute("usuario", usuario);

            model.addAttribute("subtotal", String.format("%.2f", resumen.getSubtotal()));
            model.addAttribute("impuestos", String.format("%.2f", resumen.getImpuestos()));
            model.addAttribute("descuentos", String.format("%.2f", resumen.getDescuentos()));
            model.addAttribute("cargosServicio", String.format("%.2f", resumen.getCargosServicio()));
            model.addAttribute("total", String.format("%.2f", resumen.getTotal()));
        } else {
            model.addAttribute("usuario", null);
        }
        return "reservas";
    }

    @PostMapping("/eliminarReservaHotel")
    public String eliminarReservaHotel(@RequestParam String name,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) throws MessagingException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        try {
            hotelService.eliminarReserva(usuario.getId(), name);
            String email = usuario.getEmail();
            /*servicioEmail.enviarCorreo(
                    email,
                    "Reserva de hotel eliminada - Pack&Fly",
                    "Hola " + usuario.getNombre() + ",\n\nTu reserva para el hotel '" + name + "' fue eliminada con éxito.\n\nGracias por usar Pack&Fly."
            );
            servicioEmail.enviarCorreo("ordnaelx13@gmail.com", "Cancelacion de reserva", "El usuario" + email + "cancelo su reserva.");*/

            redirectAttributes.addFlashAttribute("mensaje", "Reserva de hotel eliminada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al eliminar la reserva de hotel.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }



        return "redirect:/reservas";

    }

    @PostMapping("/eliminarReservaVuelo")
    public String eliminarReservaVuelo(@RequestParam String email,
                                       @RequestParam String fechaVuelta,
                                       @RequestParam String fechaIda,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");


        try {
            servicioReserva.eliminarReserva(email, fechaIda, fechaVuelta);

           /* servicioEmail.enviarCorreo(
                    email,
                    "Reserva de vuelo eliminada - Pack&Fly",
                    "Hola " + usuario.getNombre() +",\n\nTu reserva de vuelo del " + fechaIda + " al " + fechaVuelta + " fue eliminada correctamente.\n\nGracias por confiar en nosotros."
            );
            servicioEmail.enviarCorreo("ordnaelx13@gmail.com", "Cancelacion de reserva", "El usuario" + email + "cancelo su vuelo.");*/

            redirectAttributes.addFlashAttribute("mensaje", "Reserva de vuelo eliminada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al eliminar la reserva de vuelo.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

        return "redirect:/reservas";

    }

    @PostMapping("/eliminarReservaExcursion")
    public String eliminarReservaExcursion(@RequestParam String title,
                                           HttpServletRequest request,
                                           RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        try {
            servicioExcursiones.eliminarReserva(usuario.getId(), title);
            String email = usuario.getEmail();
            /*servicioEmail.enviarCorreo(
                    email,
                    "Reserva de excursión eliminada - Pack&Fly",
                    "Hola " + usuario.getNombre() + ",\n\nTu reserva para la excursión '" + title + "' fue eliminada.\n\nEsperamos verte en otro viaje pronto."
            );
            servicioEmail.enviarCorreo("ordnaelx13@gmail.com", "Cancelacion de reserva", "El usuario" + email + "cancelo su excursion" + title);*/

            redirectAttributes.addFlashAttribute("mensaje", "Reserva de excursion eliminada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al eliminar la reserva de excursion.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }
        return "redirect:/reservas";
    }

    @PostMapping("/editarReservaVuelo")
    public String editarReservaVuelo(
            @RequestParam Long idVuelo,
            @RequestParam String email,
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam String fechaIda,
            @RequestParam String fechaVuelta,
            RedirectAttributes redirectAttributes
    ) {
        try {
            servicioReserva.editarReserva(idVuelo, email, origen, destino, fechaIda, fechaVuelta);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de vuelo editada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al editar la reserva de vuelo.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }
        return "redirect:/reservas";
    }

    @PostMapping("/editarReservaHotel")
    public String editarReservaHotel(
            @RequestParam Long idHotel,
            @RequestParam String name,
            @RequestParam String ciudad,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer adult,
            @RequestParam Integer children,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        try {
            hotelService.editarReserva(idHotel, usuario.getId(), name, ciudad, checkIn, checkOut, adult, children);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de hotel editada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al editar la reserva de hotel.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }
        return "redirect:/reservas";
    }

    @PostMapping("/editarReservaExcursion")
    public String editarReservaExcursion(
            @RequestParam Long idExcursion,
            @RequestParam String title,
            @RequestParam String url,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        try {
            servicioExcursiones.editarReserva(idExcursion, usuario.getId(), title, url);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de excursion editada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al editar la reserva de excursion.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }
        return "redirect:/reservas";
    }

    @PostMapping("/pagar")
    public String pagar(
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
        hotelService.pagarHotelesDto(hoteles);
        servicioReserva.pagarRerservasDeVuelo(usuario.getEmail());
        servicioExcursiones.pagarExcursiones(usuario.getId());

       // ResumenPagoDto resumen = servicioLogin.obtenerDeudaDelUsuario(usuario.getId(), hotelesDto, vuelos, excursiones);
       // usuario.setApagar(resumen.getTotal());



        return "redirect:/perfil-usuario";
    }

}
