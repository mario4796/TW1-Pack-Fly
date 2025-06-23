package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class ServicioVuelosImpl implements ServicioVuelos {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final RepositorioVuelo repositorioVuelo;
    private final  ConfiguracionDeApiKey apiKeyConfig;
    private final String gl = "ar"; //geolocalizacion ar = argentina
    private final String hl = "es"; //idioma es = español
    private final String currency = "ARS"; //formato de moneda ARS = pesos argentinos

    public ServicioVuelosImpl(HttpClient httpClient, ObjectMapper objectMapper, RepositorioVuelo repositorioVuelo, ConfiguracionDeApiKey apiKeyConfig) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.repositorioVuelo = repositorioVuelo;
        this.apiKeyConfig = apiKeyConfig;
    }

    @Override
    public Vuelo getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta) {

        String API_KEY = apiKeyConfig.getApiKey();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIdaStr = dateFormat.format(fechaIda);
        String fechaVueltaStr = dateFormat.format(fechaVuelta);

        String origenEncoded = URLEncoder.encode(origen.toUpperCase(), StandardCharsets.UTF_8);
        String destinoEncoded = URLEncoder.encode(destino.toUpperCase(), StandardCharsets.UTF_8);

        String baseUrl = String.format(
                "https://serpapi.com/search.json?engine=google_flights&departure_id=%s&arrival_id=%s&outbound_date=%s&return_date=%s&currency=%s&gl=%s&hl=%s&api_key=%s",
                origenEncoded, destinoEncoded, fechaIdaStr, fechaVueltaStr, currency, gl, hl, API_KEY
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 👉 Agregá esta línea para ver la respuesta JSON en consola:
            System.out.println("Respuesta cruda de SerpAPI:\n" + response.body());

            if (response.statusCode() == 200) {
                VueloResponse result = objectMapper.readValue(response.body(), VueloResponse.class);

                if (result.getMejoresVuelos() != null && !result.getMejoresVuelos().isEmpty()) {
                    Vuelo vuelo = result.getMejoresVuelos().get(0);

                    // Guardar en base de datos
                    repositorioVuelo.guardar(vuelo);

                    return vuelo;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
