package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioHotel;
import com.tallerwebi.dominio.ServicioRecomendacion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.infraestructura.implementaciones.RepositorioExcursionImpl;
import com.tallerwebi.infraestructura.RepositorioHotel;
import com.tallerwebi.infraestructura.RepositorioReserva;
import com.tallerwebi.infraestructura.RepositorioVuelo;
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
    private final RepositorioExcursionImpl repositorioExcursionImpl;
    private final ServicioHotel servicioHotel;

    public ServicioRecomendacionImpl(
            RepositorioReserva reservaRepo,
            RepositorioVuelo vueloRepo,
            RepositorioHotel hotelRepo,
            RepositorioExcursionImpl excursionRepo,
            ServicioHotel servicioHotel
    ) {
        this.repositorioReserva = reservaRepo;
        this.repositorioVuelo = vueloRepo;
        this.repositorioHotel = hotelRepo;
        this.repositorioExcursionImpl = excursionRepo;
        this.servicioHotel = servicioHotel;
    }

    @Override
    public List<RecomendacionDTO> obtenerRecomendacionesPara(Usuario usuario) {
        List<RecomendacionDTO> recomendaciones = new ArrayList<>();


        repositorioExcursionImpl.obtenerTodas().stream().limit(3).forEach(e ->
                recomendaciones.add(new RecomendacionDTO("Excursión", e.getTitle(), e.getUrl())));


        List<HotelDto> hotelesApi = servicioHotel.buscarHoteles("Buenos Aires", "2025-10-01", "2025-10-03", 2, 0);
        hotelesApi.stream().limit(3).forEach(h ->
                recomendaciones.add(new RecomendacionDTO("Hotel", h.getName(), h.getImagen())));

        repositorioVuelo.obtenerTodos().stream().limit(3).forEach(v ->
                recomendaciones.add(new RecomendacionDTO("Vuelo", v.getDestino(), "https://via.placeholder.com/300x180.png?text=Vuelo")));

        return recomendaciones;
    }
}
