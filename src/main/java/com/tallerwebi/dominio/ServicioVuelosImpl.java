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

    public ServicioVuelosImpl(HttpClient httpClient, ObjectMapper objectMapper, RepositorioVuelo repositorioVuelo) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.repositorioVuelo = repositorioVuelo;
    }

    @Override
    public Vuelo getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIdaStr = dateFormat.format(fechaIda);
        String fechaVueltaStr = dateFormat.format(fechaVuelta);

        String origenEncoded = URLEncoder.encode(origen.toUpperCase(), StandardCharsets.UTF_8);
        String destinoEncoded = URLEncoder.encode(destino.toUpperCase(), StandardCharsets.UTF_8);

        String baseUrl = String.format(
                "https://serpapi.com/search.json?engine=google_flights&departure_id=%s&arrival_id=%s&outbound_date=%s&return_date=%s&api_key=6517d90738cfae130702ee57ecf7d765a99a1d3a0335ee16852c89eec0c6990e",
                origenEncoded, destinoEncoded, fechaIdaStr, fechaVueltaStr
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
