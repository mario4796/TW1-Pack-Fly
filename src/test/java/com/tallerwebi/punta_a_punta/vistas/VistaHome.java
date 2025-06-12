package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaHome extends VistaWeb {
    public VistaHome(Page page) {
        super(page);
        page.navigate("http://localhost:8080/spring/home");
    }

    public String obtenerTituloBienvenida() {
        return this.obtenerTextoDelElemento("a.navbar-brand");
    }

    public void darClickEnElElemento(){
        this.darClickEnElElemento("a.nav-link[href='./busqueda-vuelo']");
    }



}