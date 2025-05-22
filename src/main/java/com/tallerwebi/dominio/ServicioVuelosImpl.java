package com.tallerwebi.dominio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
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

    public Vuelo getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIdaStr = dateFormat.format(fechaIda);
        String fechaVueltaStr = dateFormat.format(fechaVuelta);
        String baseUrl =String.format("https://serpapi.com/search.json?engine=google_flights&departure_id=" + origen.toUpperCase() + "&arrival_id=" +
                destino.toUpperCase() + "&outbound_date=" + fechaIdaStr + "&return_date=" + fechaVueltaStr +
                "&api_key=6517d90738cfae130702ee57ecf7d765a99a1d3a0335ee16852c89eec0c6990e") ;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                VueloResponse result = objectMapper.readValue(response.body(), VueloResponse.class);

                if (result.getMejoresVuelos() != null && !result.getMejoresVuelos().isEmpty()) {
                    System.out.println("Respuesta API: " + response.body());
                    return result.getMejoresVuelos().get(0);
                } else if (result.getOtrosVuelos() != null && !result.getOtrosVuelos().isEmpty()) {
                    return result.getOtrosVuelos().get(0);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}



