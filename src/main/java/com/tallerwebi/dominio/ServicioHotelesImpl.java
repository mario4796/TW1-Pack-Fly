package com.tallerwebi.dominio;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServicioHotelesImpl implements ServicioHoteles {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String API_KEY = "902e4c6190ee25df47f8fd037098a1f16ac78e390eaa53a91c5daf2c930743a6";

    @Override
    public List<Hotel> getHotels(String destino, String checkIn, String checkOut) {
        List<Hotel> hoteles = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String checkInStr = formatter.format(checkIn);
        String checkOutStr = formatter.format(checkOut);

        String url = String.format(
                "https://serpapi.com/search.json?engine=google_hotels&q=%s&vacation_rentals=true&check_in_date=%s&check_out_date=%s&adults=1d&currency=USD&gl=us&hl=en&api_key=%s",

                destino, checkInStr, checkOutStr, API_KEY
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = objectMapper.readTree(response.body());

            JsonNode results = root.path("vacation_rentals_results");
            if (results.isArray()) {
                for (JsonNode nodo : results) {
                    String nombre = nodo.path("name").asText();
                    String descripcion = nodo.path("description").asText("");
                    String precio = nodo.path("price").asText("$?");

                    Hotel hotel = new Hotel(nombre, descripcion, precio);
                    hoteles.add(hotel);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return hoteles;
    }
}


