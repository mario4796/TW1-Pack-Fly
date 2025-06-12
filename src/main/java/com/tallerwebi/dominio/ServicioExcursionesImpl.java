// src/main/java/com/tallerwebi/dominio/ServicioExcursionesImpl.java
// src/main/java/com/tallerwebi/dominio/ServicioExcursionesImpl.java
package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.Excursion; // Asegúrate de importar Excursion
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional; // O si usas Spring: import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@Transactional // Puedes poner @Transactional a nivel de clase o en métodos individuales
public class ServicioExcursionesImpl implements ServicioExcursiones {
    //a59fc601949d79d62505d4a3c668dedf8e6e4c2756bd401124e13f7c1a4b6ad6
    private final String apiKey  = "902e4c6190ee25df47f8fd037098a1f16ac78e390eaa53a91c5daf2c930743a6";
    //private final String apiKey  = "1d9b2f7b6812e654ec3ab0f399081e03a4402ff91bf6f50ef00bb403d2014118";
    private static final String BASE_URL = "https://serpapi.com/search";
    private static final String ENGINE   = "google_events";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper   = new ObjectMapper();

    // 1. DECLARACIÓN DEL REPOSITORIO
    private RepositorioExcursion repositorioExcursion;

    // 2. CONSTRUCTOR PARA INYECTAR EL REPOSITORIO
    @Autowired // Spring usará este constructor para inyectar la dependencia
    public ServicioExcursionesImpl(RepositorioExcursion repositorioExcursion) {
        this.repositorioExcursion = repositorioExcursion;
    }

    @Override
    public List<ExcursionDTO> getExcursiones(String location, String query) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("SERPAPI_API_KEY no definida");
        }
        try {
            String fullQuery = query + " in " + location;
            String url = String.format(
                    "%s?engine=%s&api_key=%s&q=%s&location=%s",
                    BASE_URL,
                    URLEncoder.encode(ENGINE,   StandardCharsets.UTF_8),
                    URLEncoder.encode(apiKey,   StandardCharsets.UTF_8),
                    URLEncoder.encode(fullQuery,StandardCharsets.UTF_8),
                    URLEncoder.encode(location, StandardCharsets.UTF_8)
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
                JsonNode root = mapper.readTree(response.body());
                List<ExcursionDTO> lista =
                        mapper.convertValue(root.get("events_results"),
                                new TypeReference<List<ExcursionDTO>>(){});
                return Collections.unmodifiableList(lista);
            } else {
                System.err.println("Error HTTP " + response.statusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    // 3. IMPLEMENTACIÓN DEL NUEVO MÉT ODO TRANSACCIONAL
    @Override
    // Si @Transactional está a nivel de clase, no es estrictamente necesario aquí, pero es buena práctica para claridad.
    // Si la anotación de clase es de javax.transaction, entonces sí es necesario aquí.
    @Transactional
    public void guardarExcursion(Excursion excursion) {
        repositorioExcursion.guardar(excursion); // Aquí se usa la variable repositorioExcursion
    }
}