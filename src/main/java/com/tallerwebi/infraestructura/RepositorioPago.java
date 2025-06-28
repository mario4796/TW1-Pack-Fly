package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.entidades.Pago;

public interface RepositorioPago {
    void guardar(Pago pago);
}