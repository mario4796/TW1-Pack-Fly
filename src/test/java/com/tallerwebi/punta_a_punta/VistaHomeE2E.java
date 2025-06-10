package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaHome;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class VistaHomeE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaHome vistaHome;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaHome = new VistaHome(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaMostrarPackAndFlyEnElNavbar() {
        String texto = vistaHome.obtenerTituloBienvenida();
        assertThat(texto, containsStringIgnoringCase("Pack&Fly"));
    }

    @Test
    void debriaNavegarABusquedaVuelosDesdeElHome(){
        vistaHome.darClickEnElElemento();
        String url = vistaHome.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/busqueda-vuelo"));
    }
}