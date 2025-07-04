package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.Excursion;
import com.tallerwebi.dominio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Controlador para el flujo de pago de reservas de vuelo, hotel y/o excursión.
 */
@Controller
public class ControladorPago {

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


    @GetMapping("/pago-formulario")
    public String mostrarFormularioPago(
        /*    @RequestParam("reservaId") Long reservaId,
            @RequestParam(value = "hotelId", required = false) Long hotelId,
            @RequestParam(value = "excursionId", required = false) Long excursionId,*/
            Model model,
            HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);
    /*
        // Obtener entidades por ID
        Reserva reserva     = servicioReserva.buscarPorId(reservaId);
        Hotel   hotel       = (hotelId != null)     ? servicioHotel.buscarPorId(hotelId)       : null;
        Excursion excursion = (excursionId != null) ? servicioExcursiones.buscarPorId(excursionId) : null;

        model.addAttribute("reserva", reserva);
        model.addAttribute("hotel", hotel);
        model.addAttribute("excursion", excursion);
        model.addAttribute("reservaId", reservaId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("excursionId", excursionId); */
        return "pagoFormulario";
    }


    @PostMapping("/pagar")
    public String pagar(
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");


        try {
            List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
            hotelService.pagarHotelesDto(hoteles);
            servicioReserva.pagarRerservasDeVuelo(usuario.getEmail());
            servicioExcursiones.pagarExcursiones(usuario.getId());
            redirectAttributes.addFlashAttribute("mensaje", "Pago realizado con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al realizar el pago.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }
        try {
            List<Hotel> hoteles = hotelService.buscarReservas(usuario.getId());
            List<Reserva> vuelos = servicioReserva.obtenerReservasPorEmail(usuario.getEmail());
            List<Excursion> excursiones = servicioExcursiones.obtenerExcursionesDeUsuario(usuario.getId());

            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("Hola ").append(usuario.getNombre()).append(",\n\n")
                    .append("Te confirmamos que tu pago fue realizado con éxito.\n")
                    .append("Aquí está el detalle de tus reservas:\n\n");

            cuerpo.append("✈️ Vuelos:\n");
            if (vuelos.isEmpty()) {
                cuerpo.append("  - No hay vuelos reservados.\n");
            } else {
                for (Reserva vuelo : vuelos) {
                    cuerpo.append(" | Fecha ida: ").append(vuelo.getFechaIda())
                            .append(" | Destino: ").append(vuelo.getDestino()).append("\n");
                }
            }

            cuerpo.append("\n🏨 Hoteles:\n");
            if (hoteles.isEmpty()) {
                cuerpo.append("  - No hay hoteles reservados.\n");
            } else {
                for (Hotel hotel : hoteles) {
                    cuerpo.append(" | Nombre: ").append(hotel.getName())
                            .append(" | Check-in: ").append(hotel.getCheckIn()).append("\n");
                }
            }

            cuerpo.append("\n🧭 Excursiones:\n");
            if (excursiones.isEmpty()) {
                cuerpo.append("  - No hay excursiones reservadas.\n");
            } else {
                for (Excursion excursion : excursiones) {
                    cuerpo.append(" | Nombre: ").append(excursion.getTitle())
                            .append(" | Lugar: ").append(excursion.getLocation()).append("\n");
                }

            }


            cuerpo.append("\n¡Gracias por elegir Pack&Fly!\n");

           servicioEmail.enviarCorreo(usuario.getEmail(), "Confirmación de pago - Pack&Fly", cuerpo.toString());
            redirectAttributes.addFlashAttribute("mensaje", "Mail enviado con exito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al enviar el mail.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }


        // ResumenPagoDto resumen = servicioLogin.obtenerDeudaDelUsuario(usuario.getId(), hotelesDto, vuelos, excursiones);
        // usuario.setApagar(resumen.getTotal());



        return "redirect:/perfil-usuario";
    }
}
