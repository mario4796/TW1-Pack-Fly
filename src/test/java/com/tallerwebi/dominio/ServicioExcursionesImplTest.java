package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ServicioExcursionesImplTest {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private RepositorioExcursion repositorioExcursion;
    private ServicioExcursionesImpl servicio;


    @BeforeEach
    public void setUp() {
        repositorioExcursion = mock(RepositorioExcursion.class);
        servicio = new ServicioExcursionesImpl(repositorioExcursion);

        httpClient = mock(HttpClient.class);
        objectMapper = mock(ObjectMapper.class);

        ReflectionTestUtils.setField(servicio, "apiKey",
                "902e4c6190ee25df47f8fd037098a1f16ac78e390eaa53a91c5daf2c930743a6");
        ReflectionTestUtils.setField(servicio, "httpClient", httpClient);
        ReflectionTestUtils.setField(servicio, "mapper", objectMapper);
    }

    @Test
    public void getExcursiones_respuestaValida_retornaListaDeExcursiones() throws Exception {
        // Configuración del test
        String fakeJson = "{\"events_results\":[{\"title\":\"Cataratas\",\"start_date\":\"2025-01-01\",\"location\":\"Misiones\",\"description\":\"Paseo\",\"link\":\"http://ejemplo.com\",\"price\":1500.0}]}";

        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(fakeJson);
        when(httpResponse.statusCode()).thenReturn(200);

        JsonNode rootNode = mock(JsonNode.class);
        JsonNode eventsNode = mock(JsonNode.class);

        when(objectMapper.readTree(fakeJson)).thenReturn(rootNode);
        when(rootNode.get("events_results")).thenReturn(eventsNode);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        // Ejecución
        List<ExcursionDTO> resultado = servicio.getExcursiones("Buenos Aires", "naturaleza");

        // Verificación
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cataratas", resultado.get(0).getTitle());

        verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    public void getExcursiones_errorHttp_retornaListaVacia() throws Exception {
        // Configuración
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(500);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        // Ejecución
        List<ExcursionDTO> resultado = servicio.getExcursiones("X", "Y");

        // Verificación
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}

