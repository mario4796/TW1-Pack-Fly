package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.VueloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.mail.MessagingException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorVuelos {


    @Autowired
    private ServicioEmail servicioEmail;


    @Autowired
    private ServicioReserva servicioReserva;


   @Autowired
    public ControladorVuelos(ServicioReserva servicioReservas) {
        this.servicioReserva = servicioReservas;
    }

    @GetMapping("/busqueda-vuelo")
    public String vistaBusquedaVuelo(HttpServletRequest request,
                                     Model model) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);
        return "busqueda-vuelo";
    }

    @PostMapping("/busqueda-vuelo")
    public String buscarVuelo(
            @RequestParam String origen,
            @RequestParam String destino,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaIda,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaVuelta,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            HttpServletRequest request,
            Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);

        List<VueloDTO> vuelos = servicioReserva.getVuelo(origen, destino, fechaIda, fechaVuelta);

        if (vuelos != null && !vuelos.isEmpty()) {
            if (precioMin != null && precioMax != null) {
                vuelos = vuelos.stream()
                        .filter(v -> v.getPrecio() >= precioMin && v.getPrecio() <= precioMax)
                        .collect(Collectors.toList());  // ✅ compatible con Java 11

                if (vuelos.isEmpty()) {
                    model.addAttribute("error", "No hay vuelos en el rango de precio indicado.");
                }
            }

            model.addAttribute("vuelos", vuelos); // <- Se usa en el HTML con th:each
        } else {
            model.addAttribute("error", "Vuelo no encontrado");
        }

        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("origen", origen);
        model.addAttribute("destino", destino);
        model.addAttribute("fechaIda", fechaIda);
        model.addAttribute("fechaVuelta", fechaVuelta);

        return "busqueda-vuelo";
    }


    @GetMapping("/formulario-reserva")
    public String mostrarFormularioVacio() {return "formularioReserva";}

    @PostMapping("/formulario-reserva")
    public String mostrarFormularioReserva(@RequestParam String origen,
                                           @RequestParam String destino,
                                           @RequestParam String fechaIda,
                                           @RequestParam String fechaVuelta,
                                           @RequestParam Double precio,
                                           HttpServletRequest request,
                                           Model model) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);

        model.addAttribute("origen", origen);
        model.addAttribute("destino", destino);
        model.addAttribute("fechaIda", fechaIda);
        model.addAttribute("fechaVuelta", fechaVuelta);
        model.addAttribute("precio", precio);
        return "formularioReserva";
    }
/*
    @PostMapping("/guardar-reserva")
    public String guardarReserva(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaIda") String fechaIda,
            @RequestParam("fechaVuelta") String fechaVuelta,
            @RequestParam("precio") Double precio,
            Model model
    ) {
        Reserva reserva = new Reserva(nombre, email, origen, destino, fechaIda, fechaVuelta, precio);
        servicioReserva.guardarReserva(reserva);
        return "redirect:/busqueda-hoteles?reservaExitosa=true";
    }*/

    @PostMapping("/guardar-vuelo")
    public String guardarReserva(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("origen") String origen,
            @RequestParam("destino") String destino,
            @RequestParam("fechaIda") String fechaIda,
            @RequestParam("fechaVuelta") String fechaVuelta,
            @RequestParam("precio") Double precio,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");

        VueloDTO dto = new VueloDTO();
        dto.setOrigen(origen);
        dto.setDestino(destino);
        dto.setFechaIda(fechaIda);
        dto.setFechaVuelta(fechaVuelta);
        dto.setPrecio(precio);

        Vuelo vuelo = dto.toEntidad(nombre, email, usuario);
        try {
            servicioReserva.guardarReserva(vuelo);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de vuelo creada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al crear la reserva de vuelo.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

       try {
            servicioEmail.enviarCorreo(
                    email,
                    "Confirmación de Reserva - Pack&Fly",
                    "¡Gracias por tu reserva, " + usuario.getNombre() + "\n"
                            + "Vuelo: " + origen + " → " + destino + "\n"
                            + "Fecha ida: " + fechaIda + "\n"
                            + "Fecha vuelta:" + fechaVuelta + "\n"
                            + "Precio: $" + precio + "\n"
                            + "Recorda que tenes hasta 7 dias antes de la reservacion para pagar, si no su reservacion sera ELIMINADA"

            );
            servicioEmail.enviarCorreo("ordnaelx13@gmail.com", "Nueva reserva de vuelo",
                    "El usuario "+email+" ha reservado un vuelo de " + origen+ " a "+ destino+"\n"
                    + "Fecha ida: " + fechaIda + "\n"
                    + "Fecha vuelta:" + fechaVuelta + "\n"
                    + "Precio: $" + precio + "\n");
        } catch (Exception ex) {
            System.err.println("Error al enviar email de reserva de vuelo: " + ex.getMessage());
        }

        return "redirect:/busqueda-hoteles";
    }



    /*@GetMapping("/ver-reservas")
    public String verReservas(@RequestParam("email") String email, Model model) {
        List<Reserva> reservas = servicioReserva.obtenerReservasPorEmail(email);
        model.addAttribute("reservas", reservas);
        model.addAttribute("email", email);
        return "verReservas";
    }*/


}
