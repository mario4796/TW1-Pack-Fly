package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.config.ConfiguracionDeApiKey;
import com.tallerwebi.dominio.implementaciones.ServicioVuelosImpl;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.infraestructura.RepositorioVuelo;
import com.tallerwebi.presentacion.response.VueloResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServicioVuelosImplTest {

    @Mock private HttpClient            httpClient;
    @Mock private ObjectMapper          objectMapper;
    @Mock private RepositorioVuelo      repositorioVuelo;
    @Mock private ConfiguracionDeApiKey apiKeyConfig;
    @Mock private HttpResponse<String>  httpResponse;

    @InjectMocks private ServicioVuelosImpl servicioVuelos;

    private Date fechaIda, fechaVuelta;

    @Before
    public void setUp() throws Exception {
        fechaIda    = new SimpleDateFormat("yyyy-MM-dd").parse("2025-07-10");
        fechaVuelta = new SimpleDateFormat("yyyy-MM-dd").parse("2025-07-20");
    }

    @Test
    public void getVuelo_conResultado_guardaYRetorna() throws Exception {
        when(apiKeyConfig.getApiKey()).thenReturn("DUMMY_KEY");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn((HttpResponse) httpResponse);

        String jsonConVuelo = "{\"mejoresVuelos\":[{}]}";
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(jsonConVuelo);

        VueloResponse respCon = mock(VueloResponse.class);
        Vuelo vueloMock = new Vuelo();
        when(respCon.getMejoresVuelos()).thenReturn(Collections.singletonList(vueloMock));
        when(objectMapper.readValue(eq(jsonConVuelo), eq(VueloResponse.class)))
                .thenReturn(respCon);

        Vuelo resultado = servicioVuelos.getVuelo("EZE", "MAD", fechaIda, fechaVuelta);

        assertSame("Debe devolver el vuelo parseado", vueloMock, resultado);
        verify(repositorioVuelo).guardar(vueloMock);
    }

    @Test
    public void getVuelo_sinResultadosLista_retornaNull() throws Exception {
        when(apiKeyConfig.getApiKey()).thenReturn("DUMMY_KEY");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn((HttpResponse) httpResponse);

        String jsonVacio = "{\"mejoresVuelos\":[]}";
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(jsonVacio);

        VueloResponse respEmpty = mock(VueloResponse.class);
        when(respEmpty.getMejoresVuelos()).thenReturn(Collections.emptyList());
        when(objectMapper.readValue(eq(jsonVacio), eq(VueloResponse.class)))
                .thenReturn(respEmpty);

        assertNull("Debe retornar null cuando no hay vuelos", servicioVuelos.getVuelo("X", "Y", fechaIda, fechaVuelta));
        verify(repositorioVuelo, never()).guardar(any());
    }

    @Test
    public void getVuelo_statusNo200_retornaNull() throws Exception {
        when(apiKeyConfig.getApiKey()).thenReturn("DUMMY_KEY");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn((HttpResponse) httpResponse);

        // Aunque el JSON tenga vuelos, status != 200 impide procesar
        String jsonConVuelo = "{\"mejoresVuelos\":[{}]}";
        when(httpResponse.statusCode()).thenReturn(500);
        when(httpResponse.body()).thenReturn(jsonConVuelo);

        // No stub de objectMapper.readValue porque no debería llamarse
        assertNull("Debe retornar null si statusCode != 200", servicioVuelos.getVuelo("A", "B", fechaIda, fechaVuelta));
        verifyNoInteractions(objectMapper);
        verify(repositorioVuelo, never()).guardar(any());
    }

    @Test
    public void getVuelo_resultadoNullLista_retornaNull() throws Exception {
        when(apiKeyConfig.getApiKey()).thenReturn("DUMMY_KEY");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn((HttpResponse) httpResponse);

        String jsonNulo = "{}";  // sin campo mejoresVuelos
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(jsonNulo);

        VueloResponse respNull = mock(VueloResponse.class);
        when(respNull.getMejoresVuelos()).thenReturn(null);
        when(objectMapper.readValue(eq(jsonNulo), eq(VueloResponse.class)))
                .thenReturn(respNull);

        assertNull("Debe retornar null cuando getMejoresVuelos() es null",
                servicioVuelos.getVuelo("C", "D", fechaIda, fechaVuelta));
        verify(repositorioVuelo, never()).guardar(any());
    }

    @Test
    public void getVuelo_errorIOException_retornaNull() throws Exception {
        when(apiKeyConfig.getApiKey()).thenReturn("DUMMY_KEY");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException());

        assertNull("Debe retornar null ante IOException",
                servicioVuelos.getVuelo("E", "F", fechaIda, fechaVuelta));
        verifyNoInteractions(repositorioVuelo);
    }

    @Test
    public void getVuelo_errorInterruptedException_retornaNull() throws Exception {
        when(apiKeyConfig.getApiKey()).thenReturn("DUMMY_KEY");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException());

        assertNull("Debe retornar null ante InterruptedException",
                servicioVuelos.getVuelo("G", "H", fechaIda, fechaVuelta));
        verifyNoInteractions(repositorioVuelo);
    }
}
