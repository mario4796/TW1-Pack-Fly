package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioHotelImpl implements ServicioHotel {
    private final String API_KEY = "a59fc601949d79d62505d4a3c668dedf8e6e4c2756bd401124e13f7c1a4b6ad6";

    public List<Hotel> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children, String children_ages) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(
                "https://serpapi.com/search.json?engine=google_hotels&q=%s&check_in_date=%s&check_out_date=%s&adults=%d&children=%d&children_ages=%s&currency=ARS&api_key=%s",
                ciudad, checkIn, checkOut, adults, children, children_ages, API_KEY
        );
        System.out.println("URL de consulta: " + url);

        HotelResponse response = restTemplate.getForObject(url, HotelResponse.class);
        return response != null ? response.getProperties() : List.of();
    }
}
