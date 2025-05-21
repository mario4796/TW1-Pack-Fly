package com.tallerwebi.dominio;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioHotelesImpl implements ServicioHoteles {

    private final String API_KEY = "902e4c6190ee25df47f8fd037098a1f16ac78e390eaa53a91c5daf2c930743a6";

    @Override
    public List<Hotel> getHotels(HotelResponse hotelResponse) {
        List<Hotel> hoteles = new ArrayList<>();
        try {
            String url = String.format(
                    "https://serpapi.com/search.json?engine=google_hotels&q=%s&vacation_rentals=true&check_in_date=%s&check_out_date=%s&adults=%d&currency=USD&gl=us&hl=en&api_key=%s",
                    hotelResponse.getLocation(),
                    hotelResponse.getCheckInDate(),
                    hotelResponse.getCheckOutDate(),
                    hotelResponse.getAdults(),
                    API_KEY
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            JsonNode results = root.path("vacation_rentals_results");
            if (results.isArray()) {
                for (JsonNode node : results) {
                    String name = node.path("name").asText("");
                    String desc = node.path("description").asText("");
                    String price = node.path("price").asText("");

                    hoteles.add(new Hotel(name, desc, price));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hoteles;
    }
}

