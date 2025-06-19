package com.tallerwebi.dominio.implementaciones;

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
    private final String API_KEY = "cde27281bb7ca3316860cd43fb4d85c229615d4291c18787b26595b73bb92014";
    private final String gl = "ar"; //geolocalizacion ar = argentina
    private final String hl = "es"; //idioma es = español
    private final String currency = "ARS"; //formato de moneda ARS = pesos argentinos

    private RepositorioHotelImp repositorioHotelImp;

    public ServicioHotelImpl(RepositorioHotelImp repositorioHotelImp) {
        this.repositorioHotelImp = repositorioHotelImp;
    }

    public List<HotelDto> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children, String children_ages) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(
                "https://serpapi.com/search.json?engine=google_hotels&q=%s&check_in_date=%s&check_out_date=%s&adults=%d&children=%d&children_ages=%s&currency=%s&gl=%s&hl=%s&api_key=%s",
                ciudad, checkIn, checkOut, adults, children, children_ages, currency, gl, hl, API_KEY
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
    public List<Hotel> buscarReservas(Long idUsuario) {
       return repositorioHotelImp.buscarReserva(idUsuario);
    }

    @Override
    public void eliminarReserva(Long idUsuario, String nameHotel) {
       repositorioHotelImp.eliminarReserva(idUsuario, nameHotel);
    }

    @Override
    public void editarReserva(Long idHotel, Long idUsuario, String name, String newName, String ciudad, String checkIn, String checkout, Integer adults, Integer children) {
        Hotel reserva = repositorioHotelImp.buscarPorUsuarioYNombre(idUsuario, idHotel);
        if (reserva != null) {
            reserva.setName(newName);
            reserva.setCiudad(ciudad);
            reserva.setCheckIn(checkIn);
            reserva.setCheckOut(checkout);
            reserva.setAdult(adults);
            reserva.setChildren(children);
            repositorioHotelImp.actualizar(reserva);
        }
    }


}
