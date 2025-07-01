package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.ReservaPagada;

public interface RepositorioReservaPagada {
    void guardar(ReservaPagada reservaPagada);
}