package com.tallerwebi.dominio.implementaciones;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.config.ConfiguracionDeApiKey;
import com.tallerwebi.dominio.ServicioPreferenciaUsuario;
import com.tallerwebi.dominio.ServicioReserva;
import com.tallerwebi.dominio.entidades.Vuelo;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.RepositorioReserva;
import com.tallerwebi.presentacion.dtos.AeropuertoDTO;
import com.tallerwebi.presentacion.dtos.EscalaDTO;
import com.tallerwebi.presentacion.dtos.SegmentoVueloDTO;
import com.tallerwebi.presentacion.dtos.VueloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public List<VueloDTO> getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta, String moneda, String tipoViaje) {
        String apiKey = apiKeyConfig.getApiKey();

        int tipo = "IDA".equalsIgnoreCase(tipoViaje) ? 2 : 1;




        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("API Key de SerpAPI no definida");
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaIdaStr = dateFormat.format(fechaIda);

           // String fechaIdaStr = fechaIda != null ? dateFormat.format(fechaIda) : "";
            //String fechaVueltaStr = fechaVuelta != null ? dateFormat.format(fechaVuelta) : "";

           /* String baseUrl = String.format(
                    "https://serpapi.com/search.json?engine=google_flights&departure_id=%s&arrival_id=%s&outbound_date=%s&return_date=%s&currency=%s&gl=ar&hl=es&api_key=%s",
                    URLEncoder.encode(origen, StandardCharsets.UTF_8),
                    URLEncoder.encode(destino, StandardCharsets.UTF_8),
                    fechaIdaStr,
                    fechaVueltaStr,
                    URLEncoder.encode(moneda, StandardCharsets.UTF_8),
                    URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
            );*/

            StringBuilder sb = new StringBuilder(
                                                "https://serpapi.com/search.json?engine=google_flights"
                                                        + "&departure_id=" + URLEncoder.encode(origen, StandardCharsets.UTF_8)
                                                        + "&arrival_id="   + URLEncoder.encode(destino, StandardCharsets.UTF_8)
                                                        + "&outbound_date=" + fechaIdaStr
                                                        + "&type=" + tipo
                                                        + "&currency="      + URLEncoder.encode(moneda, StandardCharsets.UTF_8)
                                                        + "&gl=ar&hl=es"
                                                        + "&api_key="       + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                                       );

            if (tipo == 1 && fechaVuelta != null) {
                String fechaVueltaStr = dateFormat.format(fechaVuelta);
                sb.append("&return_date=").append(fechaVueltaStr);
            }
                    String baseUrl = sb.toString();

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
                        VueloDTO dto = new VueloDTO();

                        dto.setOrigen(origen);
                        dto.setDestino(destino);
                        dto.setFechaIda(fechaIdaStr);
                        //String fechaVueltaStr = dateFormat.format(fechaVuelta);
                       // dto.setFechaVuelta(fechaVueltaStr);

                        // Sólo seteo fechaVuelta si me la pasaron
                            if (fechaVuelta != null) {
                                    String fechaVueltaStr = dateFormat.format(fechaVuelta);
                                    dto.setFechaVuelta(fechaVueltaStr);
                                } else {
                                    dto.setFechaVuelta(null); // o "" si prefieres
                                }


                        // Parsear segmentos
                        JsonNode flightsArray = vueloNode.get("flights");
                        if (flightsArray != null && flightsArray.isArray()) {
                            List<SegmentoVueloDTO> segmentos = new ArrayList<>();
                            for (JsonNode seg : flightsArray) {
                                SegmentoVueloDTO segmento = new SegmentoVueloDTO();

                                // departure_airport
                                JsonNode dep = seg.get("departure_airport");
                                AeropuertoDTO depAeropuerto = new AeropuertoDTO();
                                depAeropuerto.setId(dep.get("id").asText());
                                depAeropuerto.setName(dep.get("name").asText());
                                depAeropuerto.setTime(dep.get("time").asText());
                                segmento.setDepartureAirport(depAeropuerto);

                                // arrival_airport
                                JsonNode arr = seg.get("arrival_airport");
                                AeropuertoDTO arrAeropuerto = new AeropuertoDTO();
                                arrAeropuerto.setId(arr.get("id").asText());
                                arrAeropuerto.setName(arr.get("name").asText());
                                arrAeropuerto.setTime(arr.get("time").asText());
                                segmento.setArrivalAirport(arrAeropuerto);

                                segmento.setDuration(seg.get("duration").asInt());
                                segmento.setAirplane(seg.get("airplane").asText());
                                segmento.setAirline(seg.get("airline").asText());
                                segmento.setAirlineLogo(seg.get("airline_logo").asText());
                                segmento.setTravelClass(seg.get("travel_class").asText());
                                segmento.setFlightNumber(seg.get("flight_number").asText());

                                // extensions y ticket_also_sold_by (arrays de strings)
                                List<String> extensions = new ArrayList<>();
                                if (seg.has("extensions") && seg.get("extensions").isArray()) {
                                    for (JsonNode ext : seg.get("extensions")) {
                                        extensions.add(ext.asText());
                                    }
                                }
                                segmento.setExtensions(extensions);

                                List<String> sellers = new ArrayList<>();
                                if (seg.has("ticket_also_sold_by") && seg.get("ticket_also_sold_by").isArray()) {
                                    for (JsonNode seller : seg.get("ticket_also_sold_by")) {
                                        sellers.add(seller.asText());
                                    }
                                }
                                segmento.setTicketAlsoSoldBy(sellers);

                                segmento.setLegroom(seg.has("legroom") ? seg.get("legroom").asText() : null);
                                segmento.setOvernight(seg.has("overnight") ? seg.get("overnight").asBoolean() : false);
                                segmento.setOftenDelayedByOver30Min(seg.has("often_delayed_by_over_30_min") ? seg.get("often_delayed_by_over_30_min").asBoolean() : false);
                                segmento.setPlaneAndCrewBy(seg.has("plane_and_crew_by") ? seg.get("plane_and_crew_by").asText() : null);

                                segmentos.add(segmento);
                            }
                            dto.setFlights(segmentos);
                        }

                        // Parsear escalas (layovers)
                        JsonNode layoversArray = vueloNode.get("layovers");
                        if (layoversArray != null && layoversArray.isArray()) {
                            List<EscalaDTO> escalas = new ArrayList<>();
                            for (JsonNode layover : layoversArray) {
                                EscalaDTO escala = new EscalaDTO();
                                escala.setDuration(layover.get("duration").asInt());
                                escala.setName(layover.get("name").asText());
                                escala.setId(layover.get("id").asText());
                                escala.setOvernight(layover.has("overnight") ? layover.get("overnight").asBoolean() : false);
                                escalas.add(escala);
                            }
                            dto.setLayovers(escalas);
                        }

                        dto.setDuracionTotal(vueloNode.get("total_duration").asInt());
                        dto.setPrecio(vueloNode.get("price").asInt());

                        vuelos.add(dto);
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
        if (fechaVuelta == null || fechaVuelta.isBlank()) {
            repositorioReserva.eliminarReservaSinFechaVuelta(email, fechaIda);
            System.out.println("👉 Eliminando vuelo SIN fecha de vuelta");

        } else {
            repositorioReserva.eliminarReserva(email, fechaIda, fechaVuelta);
            System.out.println("👉 Eliminando vuelo CON fecha de vuelta");

        }
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
                        LocalDate fechaIda = LocalDate.parse(r.getFechaIda());
                        return fechaIda.isAfter(LocalDate.now().minusDays(dias));
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