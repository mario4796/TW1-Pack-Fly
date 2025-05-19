package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorExcursion {

    @RequestMapping("/excursiones")
    public ModelAndView irAExcursiones() {
        return new ModelAndView("excursiones");
    }
}
