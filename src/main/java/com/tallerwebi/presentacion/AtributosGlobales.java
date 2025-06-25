package com.tallerwebi.presentacion;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AtributosGlobales {

    @ModelAttribute("datosLogin")
    public DatosLogin getDatosLogin() {
        return new DatosLogin();
    }
}
