package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
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

    public ServicioRecomendacionImpl(RepositorioReserva reservaRepo,
                                     RepositorioVuelo vueloRepo,
                                     RepositorioHotel hotelRepo,
                                     RepositorioExcursion excursionRepo) {
        this.repositorioReserva = reservaRepo;
        this.repositorioVuelo = vueloRepo;
        this.repositorioHotel = hotelRepo;
        this.repositorioExcursion = excursionRepo;
    }

    @Override
    public List<RecomendacionDTO> obtenerRecomendacionesPara(Usuario usuario) {
        List<RecomendacionDTO> recomendaciones = new ArrayList<>();

        // Recomendaciones de excursiones
        repositorioExcursion.obtenerTodas().stream().limit(3).forEach(e ->
                recomendaciones.add(new RecomendacionDTO("Excursión", e.getTitle(), e.getUrl())));

        // Recomendaciones de hoteles (usa getName en vez de getNombre, y una imagen genérica)
        repositorioHotel.obtenerTodos().stream().limit(3).forEach(h ->
                recomendaciones.add(new RecomendacionDTO("Hotel", h.getName(), "https://via.placeholder.com/300x180.png?text=Hotel")));

        // Recomendaciones de vuelos
        repositorioVuelo.obtenerTodos().stream().limit(3).forEach(v ->
                recomendaciones.add(new RecomendacionDTO("Vuelo", v.getDestino(), "https://via.placeholder.com/300x180.png?text=Vuelo")));


        return recomendaciones;
    }

}
