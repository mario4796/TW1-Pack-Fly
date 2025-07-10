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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaVuelta,
            @RequestParam(defaultValue = "IDAVUELTA") String tipoViaje,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(defaultValue="ARS") String moneda,
            HttpServletRequest request,
            Model model) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);

        if ("IDAVUELTA".equalsIgnoreCase(tipoViaje) && fechaVuelta == null) {
                    model.addAttribute("error", "Debe ingresar fecha de vuelta para Ida y vuelta");
                    model.addAttribute("tipoViaje", tipoViaje);
                    model.addAttribute("origen", origen);
                    model.addAttribute("destino", destino);
                    model.addAttribute("fechaIda", fechaIda);
                    model.addAttribute("moneda", moneda);
                    model.addAttribute("precioMin", precioMin);
                    model.addAttribute("precioMax", precioMax);
                    return "busqueda-vuelo";
                }



        List<VueloDTO> vuelos = servicioReserva.getVuelo(origen, destino, fechaIda, fechaVuelta, moneda, tipoViaje);


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

        model.addAttribute("tipoViaje", tipoViaje);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        model.addAttribute("origen", origen);
        model.addAttribute("destino", destino);
        model.addAttribute("fechaIda", fechaIda);
        model.addAttribute("fechaVuelta", fechaVuelta);
        model.addAttribute("moneda", moneda);
        request.getSession().setAttribute("VUELOS_ENCONTRADOS", vuelos);


        return "busqueda-vuelo";
    }


    @GetMapping("/formulario-reserva")
    public String mostrarFormularioVacio() {return "formularioReserva";}

    @PostMapping("/formulario-reserva")
    public String mostrarFormularioReserva(@RequestParam int idVuelo,
                                           HttpServletRequest request,
                                           Model model) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        model.addAttribute("usuario", usuario);

        List<VueloDTO> vuelos = (List<VueloDTO>) request.getSession().getAttribute("VUELOS_ENCONTRADOS");
        if (vuelos == null || idVuelo >= vuelos.size()) {
            model.addAttribute("error", "No se pudo encontrar el vuelo seleccionado.");
            return "busqueda-vuelo";
        }

        VueloDTO vueloSeleccionado = vuelos.get(idVuelo);
        request.getSession().setAttribute("VUELO_SELECCIONADO", vueloSeleccionado); // para luego guardar

        // Para completar los datos del formulario:
        model.addAttribute("origen", vueloSeleccionado.getOrigen());
        model.addAttribute("destino", vueloSeleccionado.getDestino());
        model.addAttribute("fechaIda", vueloSeleccionado.getFechaIda());
        model.addAttribute("fechaVuelta", vueloSeleccionado.getFechaVuelta());
        model.addAttribute("precio", vueloSeleccionado.getPrecio());
        model.addAttribute("idVuelo", idVuelo);

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
            @RequestParam("idVuelo") int idVuelo,  // <- NUEVO: para recuperar el vuelo
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("USUARIO");
        List<VueloDTO> vuelos = (List<VueloDTO>) request.getSession().getAttribute("VUELOS_ENCONTRADOS");
        VueloDTO vueloDTO = vuelos.get(idVuelo);

        Vuelo vuelo = vueloDTO.toEntidad(nombre, email, usuario);

        try {
            servicioReserva.guardarReserva(vuelo);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva de vuelo creada con éxito.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Hubo un error al crear la reserva de vuelo.");
            redirectAttributes.addFlashAttribute("tipo", "warning");
        }

        // ✅ Correos
        try {
            servicioEmail.enviarCorreo(
                    email,
                    "Confirmación de Reserva - Pack&Fly",
                    "¡Gracias por tu reserva, " + usuario.getNombre() + "!\n"
                            + "Vuelo: " + vuelo.getOrigen() + " → " + vuelo.getDestino() + "\n"
                            + "Fecha ida: " + vuelo.getFechaIda() + "\n"
                            + "Fecha vuelta: " + vuelo.getFechaVuelta() + "\n"
                            + "Precio: $" + vuelo.getPrecio() + "\n"
                            + "Recordá que tenés hasta 7 días antes del vuelo para pagar. Si no, será eliminado."
            );
            servicioEmail.enviarCorreo("ordnaelx13@gmail.com", "Nueva reserva de vuelo",
                    "El usuario " + email + " ha reservado un vuelo de " + vuelo.getOrigen() + " a " + vuelo.getDestino() + "\n"
                            + "Fecha ida: " + vuelo.getFechaIda() + "\n"
                            + "Fecha vuelta: " + vuelo.getFechaVuelta() + "\n"
                            + "Precio: $" + vuelo.getPrecio() + "\n");
        } catch (Exception ex) {
            System.err.println("Error al enviar email de reserva de vuelo: " + ex.getMessage());
        }

        String fechaIdaSolo = vuelo.getFechaIda().contains(" ") ? vuelo.getFechaIda().split(" ")[0] : vuelo.getFechaIda();
        String fechaVueltaSolo = (vuelo.getFechaVuelta() != null && vuelo.getFechaVuelta().contains(" "))
                ? vuelo.getFechaVuelta().split(" ")[0]
                : (vuelo.getFechaVuelta() == null ? "" : vuelo.getFechaVuelta());


        LocalDate fechaIdaVuelo = LocalDate.parse(fechaIdaSolo);
        LocalDate fechaVueltaVuelo = null;
        if (!fechaVueltaSolo.isEmpty()){
            fechaVueltaVuelo = LocalDate.parse(fechaVueltaSolo);
        }


        redirectAttributes.addFlashAttribute("destinoDeVuelo", vuelo.getDestino());
        redirectAttributes.addFlashAttribute("fechaIdaVuelo", fechaIdaVuelo);
        if (fechaVueltaVuelo != null) {
            redirectAttributes.addFlashAttribute("fechaVueltaVuelo", fechaVueltaVuelo);
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
