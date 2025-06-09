package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Pais;

public interface ServicioBanderas {
    String getBandera(String codigoPais);
    Pais getInfoPais(String codigoPais);
}
