// src/main/java/com/tallerwebi/dominio/ServicioExcursionesImpl.java
package com.tallerwebi.dominio.Implementaciones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.Excursion;
import com.tallerwebi.dominio.ServicioExcursiones;
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
    private static final String BASE_URL = "https://serpapi.com/search";
    private static final String ENGINE   = "google_events";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper   = new ObjectMapper();

    @Override
    public List<Excursion> getExcursiones(String location, String query) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("SERPAPI_API_KEY no definida");
        }
        try {
            // Une query y location en un solo parámetro q
            String fullQuery = query + " in " + location;
            String url = String.format(
                    "%s?engine=%s&api_key=%s&q=%s&location=%s",
                    BASE_URL,
                    URLEncoder.encode(ENGINE,   StandardCharsets.UTF_8),
                    URLEncoder.encode(apiKey,   StandardCharsets.UTF_8),
                    URLEncoder.encode(fullQuery,StandardCharsets.UTF_8),
                    URLEncoder.encode(location, StandardCharsets.UTF_8)  // opcional
            );

            System.out.println("Llamando a SerpApi: " + url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Respuesta SerpApi: " + response.statusCode() + " " + response.body());

            if (response.statusCode() == 200) {
                // ahora el JSON viene en la propiedad "events_results"
                JsonNode root = mapper.readTree(response.body());
                List<ExcursionImpl> lista =
                        mapper.convertValue(root.get("events_results"),
                                new TypeReference<List<ExcursionImpl>>(){});
                return Collections.unmodifiableList(lista);
            } else {
                System.err.println("Error HTTP " + response.statusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

}
