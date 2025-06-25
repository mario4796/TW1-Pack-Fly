
package com.tallerwebi.dominio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.presentacion.dtos.ExcursionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ServicioExcursionesImpl implements ServicioExcursiones {

    private final ConfiguracionDeApiKey apiConfig ;

    private static final String BASE_URL = "https://serpapi.com/search";
    private static final String ENGINE   = "google_events";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper   = new ObjectMapper();


    private RepositorioExcursion repositorioExcursion;


    @Autowired // Spring usará este constructor para inyectar la dependencia
    public ServicioExcursionesImpl(ConfiguracionDeApiKey apiConfig, RepositorioExcursion repositorioExcursion) {
        this.apiConfig = apiConfig;
        this.repositorioExcursion = repositorioExcursion;
    }

    @Override
    public List<ExcursionDTO> getExcursiones(String location, String query) {
        String apiKey = apiConfig.getApiKey();

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


    @Override

    @Transactional
    public void guardarExcursion(Excursion excursion) {
        repositorioExcursion.guardar(excursion);
    }
    @Override
    public List<Excursion> obtenerExcursionesDeUsuario(Long idUsuario) {
        return repositorioExcursion.obtenerPorUsuario(idUsuario);
    }

    @Override
    public void eliminarReserva(Long idUsuario, String title) {
        repositorioExcursion.eliminarReserva(idUsuario, title);
    }

    @Override
    public void editarReserva(Long idExcursion, Long idUsuario, String title, String url) {
        Excursion excursion = repositorioExcursion.buscarPorUsuarioYExcursion(idUsuario, idExcursion);
        if (excursion != null) {
            excursion.setTitle(title);
            excursion.setUrl(url);
            repositorioExcursion.actualizar(excursion);
        }
    }

}