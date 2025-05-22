package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Excursion;
import com.tallerwebi.dominio.ServicioExcursiones;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorExcursionTest {

    @Test
    public void verExcursiones_conValoresPorDefecto_debeInvocarServicioConDefaults() {
        // Arrange
        ServicioExcursiones servicioMock = mock(ServicioExcursiones.class);
        ControladorExcursion controlador = new ControladorExcursion(servicioMock);

        Excursion e1 = mock(Excursion.class);
        Excursion e2 = mock(Excursion.class);
        List<Excursion> excursionesPorDefecto = List.of(e1, e2);

        // Configuro el mock para los valores por defecto
        when(servicioMock.getExcursiones("Buenos Aires", "excursiones"))
                .thenReturn(excursionesPorDefecto);

        Model model = new ConcurrentModel();

        // Act: paso explícitamente los defaultValue que usa Spring
        String vista = controlador.verExcursiones("Buenos Aires", "excursiones", model);

        // Assert
        assertEquals("excursiones", vista, "Debe devolver la vista 'excursiones'");
        assertTrue(model.containsAttribute("excursiones"), "Debe agregar atributo 'excursiones'");
        @SuppressWarnings("unchecked")
        List<Excursion> resultado = (List<Excursion>) model.getAttribute("excursiones");
        assertSame(excursionesPorDefecto, resultado, "El modelo debe contener la lista retornada por el servicio");
        verify(servicioMock).getExcursiones("Buenos Aires", "excursiones");
    }

    @Test
    public void verExcursiones_conParametrosPersonalizados_debePasarlosAlServicio() {
        // Arrange
        ServicioExcursiones servicioMock = mock(ServicioExcursiones.class);
        ControladorExcursion controlador = new ControladorExcursion(servicioMock);

        List<Excursion> listaVacia = List.of();
        when(servicioMock.getExcursiones("Rosario", "turismo"))
                .thenReturn(listaVacia);

        Model model = new ConcurrentModel();

        // Act
        String vista = controlador.verExcursiones("Rosario", "turismo", model);

        // Assert
        assertEquals("excursiones", vista);
        assertEquals(listaVacia, model.getAttribute("excursiones"),
                "El modelo debe reflejar la lista que devuelva el servicio");
        verify(servicioMock).getExcursiones("Rosario", "turismo");
    }
}
