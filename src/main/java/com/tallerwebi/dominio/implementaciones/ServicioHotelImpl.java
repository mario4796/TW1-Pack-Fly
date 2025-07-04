package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.config.ConfiguracionDeApiKey;
import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.entidades.Hotel;
import com.tallerwebi.dominio.entidades.HotelResponse;
import com.tallerwebi.infraestructura.implementaciones.RepositorioHotelImp;
import com.tallerwebi.presentacion.dtos.HotelDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicioHotelImpl implements ServicioHotel {
    private final ConfiguracionDeApiKey apiconfig;
    private final String gl = "ar"; //geolocalizacion ar = argentina
    private final String hl = "es"; //idioma es = español
    private final String currency = "ARS"; //formato de moneda ARS = pesos argentinos

    private RepositorioHotelImp repositorioHotelImp;

    public ServicioHotelImpl(ConfiguracionDeApiKey apiconfig, RepositorioHotelImp repositorioHotelImp) {
        this.apiconfig = apiconfig;
        this.repositorioHotelImp = repositorioHotelImp;
    }

    public List<HotelDto> obtenerHotelesDto(List<Hotel> hoteles) {
        return hoteles.stream()
                .map(hotel -> new HotelDto(hotel.getId(), hotel.getName(), hotel.getCiudad(), hotel.getCheckIn(),
                        hotel.getCheckOut(), hotel.getAdult(), hotel.getChildren(), hotel.getPrecio(), hotel.getPagado()))
                .collect(Collectors.toList());
    }

    
    public List<HotelDto> buscarHoteles(String ciudad, String checkIn, String checkOut, Integer adults, Integer children) {

        String API_KEY = apiconfig.getApiKey();
        String children_ages = generarChildrenAges(children);

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(
                "https://serpapi.com/search.json?engine=google_hotels&q=%s&check_in_date=%s&check_out_date=%s&adults=%d&children=%d&children_ages=%s&currency=%s&gl=%s&hl=%s&api_key=%s",
                ciudad, checkIn, checkOut, adults, children, children_ages, currency, gl, hl, API_KEY
        );
        System.out.println("URL de consulta: " + url);


        HotelResponse response = restTemplate.getForObject(url, HotelResponse.class);
        return response != null ? response.getProperties() : List.of();
    }

    private String generarChildrenAges(Integer children) {
        if (children == null || children <= 0) {
            return "";
        }
        return String.join(",", java.util.Collections.nCopies(children, "10"));
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
    public void editarReserva(Long idHotel, Long idUsuario, String name, String ciudad, String checkIn, String checkout, Integer adults, Integer children) {
        Hotel reserva = repositorioHotelImp.buscarPorUsuarioYNombre(idUsuario, idHotel);
        if (reserva != null) {
            reserva.setName(name);
            reserva.setCiudad(ciudad);
            reserva.setCheckIn(checkIn);
            reserva.setCheckOut(checkout);
            reserva.setAdult(adults);
            reserva.setChildren(children);
            repositorioHotelImp.actualizar(reserva);
        }
    }

    @Override
    public Hotel buscarPorId(Long id) {
        return repositorioHotelImp.buscarPorId(id);
    }

    @Override
    public List<Hotel> buscarHotelesPagados(Long id) {
        return repositorioHotelImp.buscarHotelesPagados(id);
    }



    @Override
    public void pagarHotelesDto(List<Hotel> hoteles) {
        repositorioHotelImp.pagarHoteles(hoteles);
    }


}
