package com.tallerwebi.implementaciones;

import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.HotelResponse;
import com.tallerwebi.infraestructura.RepositorioHotelImp;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioHotelImpl implements ServicioHotel {
    private final String API_KEY = "a59fc601949d79d62505d4a3c668dedf8e6e4c2756bd401124e13f7c1a4b6ad6";

    private RepositorioHotelImp repositorioHotelImp;

    public ServicioHotelImpl(RepositorioHotelImp repositorioHotelImp) {
        this.repositorioHotelImp = repositorioHotelImp;
    }

    public List<HotelDto> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children, String children_ages) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(
                "https://serpapi.com/search.json?engine=google_hotels&q=%s&check_in_date=%s&check_out_date=%s&adults=%d&children=%d&children_ages=%s&currency=ARS&api_key=%s",
                ciudad, checkIn, checkOut, adults, children, children_ages, API_KEY
        );
        System.out.println("URL de consulta: " + url);

        HotelResponse response = restTemplate.getForObject(url, HotelResponse.class);
        return response != null ? response.getProperties() : List.of();
    }

    @Override
    public void reserva(Hotel hotel) {
       repositorioHotelImp.reservar(hotel);
    }

    @Override
    public List<HotelDto> buscarReservas(Long idUsuario) {
       return repositorioHotelImp.buscarReserva(idUsuario);
    }


}
