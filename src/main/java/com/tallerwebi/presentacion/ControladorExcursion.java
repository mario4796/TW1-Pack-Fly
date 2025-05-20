package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorExcursion {

    @RequestMapping("/excursiones")
    public ModelAndView verExcursiones() {
        ModelAndView modelAndView = new ModelAndView("excursiones");
        modelAndView.addObject("excursiones", java.util.Arrays.asList("Excursión A", "Excursión B"));
        return modelAndView;
    }
}