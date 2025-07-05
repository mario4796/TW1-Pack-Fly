package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Vuelo;
import java.util.Date;

public interface ServicioVuelos {
    Vuelo getVuelo(String origen, String destino, Date fechaIda, Date fechaVuelta);
}
