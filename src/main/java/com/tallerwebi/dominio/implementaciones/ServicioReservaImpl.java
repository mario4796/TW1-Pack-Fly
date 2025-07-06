package com.tallerwebi.dominio.implementaciones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.config.ConfiguracionDeApiKey;
import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioReserva;
import com.tallerwebi.presentacion.dtos.VueloDTO;
import com.tallerwebi.presentacion.response.VueloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

    private final RepositorioReserva repositorioReserva;


    @Autowired
    private ConfiguracionDeApiKey apiKeyConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ServicioPreferenciaUsuario servicioPreferenciaUsuario;

    @Autowired
    public ServicioReservaImpl(RepositorioReserva repositorioReserva) {
        this.repositorioReserva = repositorioReserva;
    }


    @Override
    public List<VueloDTO> getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta) {
        String apiKey = apiKeyConfig.getApiKey();

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("API Key de SerpAPI no definida");
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIdaStr = dateFormat.format(fechaIda);
            String fechaVueltaStr = dateFormat.format(fechaVuelta);

            String baseUrl = String.format(
                    "https://serpapi.com/search.json?engine=google_flights&departure_id=%s&arrival_id=%s&outbound_date=%s&return_date=%s&currency=ARS&gl=ar&hl=es&api_key=%s",
                    URLEncoder.encode(origen, StandardCharsets.UTF_8),
                    URLEncoder.encode(destino, StandardCharsets.UTF_8),
                    fechaIdaStr,
                    fechaVueltaStr,
                    URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
            );

            System.out.println("Llamando a SerpApi: " + baseUrl);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Respuesta SerpApi: " + response.statusCode());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());

                JsonNode bestFlights = root.get("best_flights");

                if (bestFlights != null && bestFlights.isArray()) {
                    List<VueloDTO> vuelos = new ArrayList<>();

                    for (JsonNode vueloNode : bestFlights) {
                        JsonNode flightsArray = vueloNode.get("flights");
                        if (flightsArray != null && flightsArray.isArray() && flightsArray.size() > 0) {
                            JsonNode primerSegmento = flightsArray.get(0);
                            JsonNode ultimoSegmento = flightsArray.get(flightsArray.size() - 1);


                            VueloDTO dto = new VueloDTO();
                            dto.setOrigen(primerSegmento.get("departure_airport").get("id").asText());
                            dto.setDestino(primerSegmento.get("arrival_airport").get("id").asText());
                            dto.setFechaIda(primerSegmento.get("departure_airport").get("time").asText());
                            dto.setFechaVuelta(ultimoSegmento.get("arrival_airport").get("time").asText());
                            dto.setPrecio(vueloNode.get("price").asInt());

                            vuelos.add(dto);
                        }
                    }

                    return Collections.unmodifiableList(vuelos);
                } else {
                    System.err.println("No se encontraron vuelos en 'best_flights'");
                }
            } else {
                System.err.println("Error HTTP " + response.statusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Collections.emptyList();
    }


    @Override
    public void guardarReserva(Vuelo vuelo) {
        repositorioReserva.guardar(vuelo);

        int cantidadAsientos = 1;
        int millas = 100;

        Usuario usuario = vuelo.getUsuario();
        if (usuario != null) {
            servicioPreferenciaUsuario.registrarReservaVuelo(usuario, cantidadAsientos, millas);
        } else {
            System.out.println("No se pudo registrar preferencias: la reserva no tiene asociado un usuario");
        }
    }

    @Override
    public List<Vuelo> obtenerReservasPorEmail(String email) {
        return repositorioReserva.buscarPorEmail(email);
    }

    @Override
    public void eliminarReserva(String email, String fechaIda, String fechaVuelta) {
        repositorioReserva.eliminarReserva(email, fechaIda, fechaVuelta);
    }

    @Override
    public void editarReserva(Long idVuelo, String email, String origen, String destino,
                              String fechaIda, String fechaVuelta) {
        Vuelo vuelo = repositorioReserva.buscarPorIdyEmail(email, idVuelo);
        if (vuelo != null) {
            vuelo.setOrigen(origen);
            vuelo.setDestino(destino);
            vuelo.setFechaIda(fechaIda);
            vuelo.setFechaVuelta(fechaVuelta);
            repositorioReserva.actualizar(vuelo);
        }
    }

    @Override
    public long contarReservasUltimosDias(String email, int dias) {
        List<Vuelo> vuelos = repositorioReserva.buscarPorEmail(email);
        return vuelos.stream()
                .filter(r -> {
                    try {
                        java.time.LocalDate fechaIda = java.time.LocalDate.parse(r.getFechaIda());
                        return fechaIda.isAfter(java.time.LocalDate.now().minusDays(dias));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();
    }

    @Override
    public Vuelo buscarPorId(Long id) {
        return repositorioReserva.buscarPorId(id);
    }

    @Override
    public void pagarRerservasDeVuelo(String email) {
        repositorioReserva.pagarReservasDeVuelo(email);
    }

    @Override
    public List<Vuelo> obtenerReservasPorEmailPagados(String email) {
        return repositorioReserva.buscarPorEmailPagadas(email);
    }
}
