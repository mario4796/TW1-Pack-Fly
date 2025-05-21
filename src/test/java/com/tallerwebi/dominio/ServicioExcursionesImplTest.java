package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
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

class ServicioExcursionesImplTest {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private ServicioExcursionesImpl servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioExcursionesImpl();

        httpClient   = mock(HttpClient.class);
        objectMapper = new ObjectMapper();

        ReflectionTestUtils.setField(servicio, "apiKey",
                "1d9b2f7b6812e654ec3ab0f399081e03a4402ff91bf6f50ef00bb403d2014118");
        ReflectionTestUtils.setField(servicio, "httpClient", httpClient);
        ReflectionTestUtils.setField(servicio, "mapper", objectMapper);
    }

    @Test
    void getExcursiones_respuestaValida_retornaListaDeExcursiones() throws Exception {
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

        List<Excursion> lista = servicio.getExcursiones("Buenos Aires", "naturaleza");

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
    void getExcursiones_errorHttp_retornaListaVacia() throws Exception {
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(500);
        // No body() because status != 200

        when(httpClient.<String>send(
                any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
        )).thenReturn(httpResponse);

        List<Excursion> lista = servicio.getExcursiones("X", "Y");

        assertNotNull(lista, "La lista no debe ser null incluso en error");
        assertTrue(lista.isEmpty(), "En HTTP 500 se debe devolver lista vacía");
    }
}
