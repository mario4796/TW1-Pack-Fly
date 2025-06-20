package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.dominio.entidades.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/reservas")
    public String vistaReservas(HttpServletRequest request, Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        if (usuario != null) {
            List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
            List<HotelDto> hotelesDto = hotelService.obtenerHotelesDto(hoteles);
            List<Reserva> vuelos = servicioReserva.obtenerReservasPorEmail(usuario.getEmail());

            List<Excursion> excursiones = servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId());

            usuario.setApagar(servicioLogin.obtenerDeudaDelUsuario(hotelesDto, vuelos, excursiones));
            model.addAttribute("vuelos", vuelos);
            model.addAttribute("hoteles", hotelesDto);
            model.addAttribute("excursiones", excursiones);
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", null);
        }
        return "reservas";
    }

    @PostMapping("/eliminarReservaHotel")
    public String eliminarReservaHotel(@RequestParam String name,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        try {
            hotelService.eliminarReserva(usuario.getId(), name);
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
                                       RedirectAttributes redirectAttributes) {

        try {
            servicioReserva.eliminarReserva(email, fechaIda, fechaVuelta);
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
            @RequestParam String email,
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam String fechaIda,
            @RequestParam String fechaVuelta,
            @RequestParam String fechaIdaOriginal,
            @RequestParam String fechaVueltaOriginal,
            RedirectAttributes redirectAttributes
    ) {
        try {
            //servicioReserva.editarReserva(email, origen, destino, fechaIda, fechaVuelta, fechaIdaOriginal, fechaVueltaOriginal);
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
            @RequestParam String newName,
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
            hotelService.editarReserva(idHotel, usuario.getId(), name, newName, ciudad, checkIn, checkOut, adult, children);
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
            @RequestParam String titleOriginal,
            @RequestParam String title,
            @RequestParam String url,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        try {
            //servicioExcursiones.editarReserva(usuario.getId(), titleOriginal, title, url);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de excursion editada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al editar la reserva de excursion.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }
        return "redirect:/reservas";
    }

}
