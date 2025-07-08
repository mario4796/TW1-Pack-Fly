package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dtos.RecomendacionDTO;
import java.util.List;

public interface ServicioRecomendacion {
    List<RecomendacionDTO> obtenerRecomendacionesPara(Usuario usuario);
}
