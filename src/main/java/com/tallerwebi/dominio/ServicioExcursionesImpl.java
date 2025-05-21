// src/main/java/com/tallerwebi/dominio/ServicioExcursionesImpl.java
package com.tallerwebi.dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
public class ServicioExcursionesImpl implements ServicioExcursiones {

    private final String apiKey  = "1d9b2f7b6812e654ec3ab0f399081e03a4402ff91bf6f50ef00bb403d2014118";
    private static final String BASE_URL = "https://serpapi.com/events.json";
    private static final String ENGINE   = "google_events";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper   = new ObjectMapper();

    @Override
    public List<Excursion> getExcursiones(String location, String query) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Variable de entorno SERPAPI_API_KEY no definida");
        }
        try {
            String url = String.format(
                    "%s?engine=%s&api_key=%s&location=%s&q=%s",
                    BASE_URL,
                    URLEncoder.encode(ENGINE,   StandardCharsets.UTF_8),
                    URLEncoder.encode(apiKey,   StandardCharsets.UTF_8),
                    URLEncoder.encode(location, StandardCharsets.UTF_8),
                    URLEncoder.encode(query,    StandardCharsets.UTF_8)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ExcursionResponse resp = mapper.readValue(response.body(), ExcursionResponse.class);
                return Collections.<Excursion>unmodifiableList(resp.getEvents());
            } else {
                System.err.println("Error HTTP " + response.statusCode() + ": " + response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
}
