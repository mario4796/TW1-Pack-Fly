package com.tallerwebi.dominio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioVuelosImpl implements ServicioVuelos {


    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ServicioVuelosImpl(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public String getVuelo(String origen, String destino, Date fecha_ida, Date fecha_vuelta) {
        String baseUrl = "https://serpapi.com/search.json?engine=google_flights&departure_id=" + origen.toUpperCase() + "&arrival_id=" +
                destino.toUpperCase() + "&departure_date=" + fecha_ida + "&return_date=" + fecha_vuelta +
                "&api_key=6517d90738cfae130702ee57ecf7d765a99a1d3a0335ee16852c89eec0c6990e";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() == 200) {
                return baseUrl;
            } else {
                return null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}



