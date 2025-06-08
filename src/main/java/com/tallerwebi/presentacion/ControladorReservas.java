package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorReservas {

    @GetMapping ("/reservas")
    public String mostrarReservas() {
        return "reservas";
    }
}
