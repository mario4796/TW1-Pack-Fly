package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEmail;
import com.tallerwebi.dominio.entidades.Reserva;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.ServicioVuelos;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
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

@Controller
public class ControladorVuelos {
    @Autowired
    private ServicioVuelos servicioVuelos;

    @Autowired
    private ServicioEmail servicioEmail;

    @Autowired
    private ServicioReserva servicioReserva;

    @Autowired
    public ControladorVuelos(ServicioVuelos servicioVuelos) {
        this.servicioVuelos = servicioVuelos;
    }

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

        Vuelo vuelo = servicioVuelos.getVuelo(origen, destino, fechaIda, fechaVuelta);

        if (vuelo != null) {
            boolean dentroRango = true;

            if (precioMin != null && precioMax != null) {
                double precioVuelo = vuelo.getPrecio();
                dentroRango = (precioVuelo >= precioMin && precioVuelo <= precioMax);
            }

            if (dentroRango) {
                model.addAttribute("vuelo", vuelo);
                model.addAttribute("vueloUrl", true);
                model.addAttribute("valorIda", vuelo.getPrecio());
                model.addAttribute("valorVuelta", vuelo.getPrecio());
            } else {
                model.addAttribute("error", "No hay vuelos en el rango de precio indicado.");
            }
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


    @PostMapping("/guardar-reserva")
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
            Model model)

            throws MessagingException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        Reserva reserva = new Reserva(nombre, email, origen, destino, fechaIda, fechaVuelta, precio);
        reserva.setUsuario(usuario); // Enlaza el usuario con la reserva

        try {
            servicioReserva.guardarReserva(reserva);
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

}
