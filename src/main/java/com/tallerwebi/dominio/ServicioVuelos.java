package com.tallerwebi.dominio;

import java.util.Date;

public interface ServicioVuelos {
    String getVuelo(String origen, String destino, Date fecha_ida, Date fecha_vuelta);

}
