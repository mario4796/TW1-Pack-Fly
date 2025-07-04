package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.config.ConfiguracionDeApiKey;
import com.tallerwebi.dominio.implementaciones.ServicioExcursionesImpl;
import com.tallerwebi.infraestructura.implementaciones.RepositorioExcursionImpl;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ServicioExcursionesImplTest {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private RepositorioExcursionImpl repositorioExcursionImpl;
    private ServicioExcursionesImpl servicio;
    private ConfiguracionDeApiKey config;

    @BeforeEach
    public void setUp() {
        config = mock(ConfiguracionDeApiKey.class);
        repositorioExcursionImpl = mock(RepositorioExcursionImpl.class);

        when(config.getApiKey()).thenReturn("fake-key");

        servicio = new ServicioExcursionesImpl(config, repositorioExcursionImpl);

        httpClient   = mock(HttpClient.class);
        objectMapper = new ObjectMapper();

        // Estos seteos ahora sí están bien
        ReflectionTestUtils.setField(servicio, "httpClient", httpClient);
        ReflectionTestUtils.setField(servicio, "mapper", objectMapper);
    }

    @Test
    public void getExcursiones_respuestaValida_retornaListaDeExcursiones() throws Exception {
        String fakeJson = "{\n" +
                "  \"events_results\": [\n" +
                "    { \"title\": \"Cataratas del Iguazú\", \"link\": \"http://ejemplo.com/iguazu\" },\n" +
                "    { \"title\": \"Perito Moreno\",     \"link\": \"http://ejemplo.com/perito\" }\n" +
                "  ]\n" +
                "}";

        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpResponse.statusCode()).thenReturn(200);

        when(httpClient.<String>send(
                any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        )).thenReturn(httpResponse);

        List<ExcursionDTO> lista = servicio.getExcursiones("Buenos Aires", "naturaleza");

        assertNotNull(lista, "La lista no debe ser null");
        assertEquals(2, lista.size(), "Debe haber 2 excursiones");
        assertEquals("Cataratas del Iguazú", lista.get(0).getTitle());
        assertEquals("Perito Moreno", lista.get(1).getTitle());

        verify(httpClient).send(
                argThat(req ->
                        req.uri().toString().contains("q=naturaleza") &&
                                req.uri().toString().contains("Buenos+Aires")
                ),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        );
    }

    @Test
    public void getExcursiones_errorHttp_retornaListaVacia() throws Exception {
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(500);
        // No body() because status != 200

        when(httpClient.<String>send(
                any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        )).thenReturn(httpResponse);

        List<ExcursionDTO> lista = servicio.getExcursiones("X", "Y");

        assertNotNull(lista, "La lista no debe ser null incluso en error");
        assertTrue(lista.isEmpty(), "En HTTP 500 se debe devolver lista vacía");
    }
}
