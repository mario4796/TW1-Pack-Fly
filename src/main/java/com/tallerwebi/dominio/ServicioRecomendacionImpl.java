package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.HotelDto;
import com.tallerwebi.presentacion.dtos.RecomendacionDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioRecomendacionImpl implements ServicioRecomendacion {

    private final RepositorioReserva repositorioReserva;
    private final RepositorioVuelo repositorioVuelo;
    private final RepositorioHotel repositorioHotel;
    private final RepositorioExcursion repositorioExcursion;
    private final ServicioHotel servicioHotel;

    public ServicioRecomendacionImpl(
            RepositorioReserva reservaRepo,
            RepositorioVuelo vueloRepo,
            RepositorioHotel hotelRepo,
            RepositorioExcursion excursionRepo,
            ServicioHotel servicioHotel
    ) {
        this.repositorioReserva = reservaRepo;
        this.repositorioVuelo = vueloRepo;
        this.repositorioHotel = hotelRepo;
        this.repositorioExcursion = excursionRepo;
        this.servicioHotel = servicioHotel;
    }

    @Override
    public List<RecomendacionDTO> obtenerRecomendacionesPara(Usuario usuario) {
        List<RecomendacionDTO> recomendaciones = new ArrayList<>();

        // Excursiones
        repositorioExcursion.obtenerTodas().stream().limit(3).forEach(e ->
                recomendaciones.add(new RecomendacionDTO("Excursión", e.getTitle(), e.getUrl())));

        // Hoteles desde la API
        List<HotelDto> hotelesApi = servicioHotel.buscarHoteles("Buenos Aires", "2025-10-01", "2025-10-03", 2, 0, "");
        hotelesApi.stream().limit(3).forEach(h ->
                recomendaciones.add(new RecomendacionDTO("Hotel", h.getName(), h.getImagen())));

        // Vuelos (aún usando imagen fija)
        repositorioVuelo.obtenerTodos().stream().limit(3).forEach(v ->
                recomendaciones.add(new RecomendacionDTO("Vuelo", v.getDestino(), "https://via.placeholder.com/300x180.png?text=Vuelo")));

        return recomendaciones;
    }
}
