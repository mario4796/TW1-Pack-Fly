package com.tallerwebi.dominio.entidades;

import javax.persistence.*;

@Entity
public class PreferenciaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario;

    private String tipoViajeVuelo; // "Solitario" o "Familiar"
    private Boolean viajeroFrecuente; // true si viajó más de una vez en las últimas 2 semanas
    private Integer millasAcumuladas;

    private String tipoHospedaje; // "Solitario" o "Familiar"
    private String tipoExcursionPreferida; // "Aventura", "Cultural", etc.

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoViajeVuelo() {
        return tipoViajeVuelo;
    }

    public void setTipoViajeVuelo(String tipoViajeVuelo) {
        this.tipoViajeVuelo = tipoViajeVuelo;
    }

    public Boolean getViajeroFrecuente() {
        return viajeroFrecuente;
    }

    public void setViajeroFrecuente(Boolean viajeroFrecuente) {
        this.viajeroFrecuente = viajeroFrecuente;
    }

    public Integer getMillasAcumuladas() {
        return millasAcumuladas;
    }

    public void setMillasAcumuladas(Integer millasAcumuladas) {
        this.millasAcumuladas = millasAcumuladas;
    }

    public String getTipoHospedaje() {
        return tipoHospedaje;
    }

    public void setTipoHospedaje(String tipoHospedaje) {
        this.tipoHospedaje = tipoHospedaje;
    }

    public String getTipoExcursionPreferida() {
        return tipoExcursionPreferida;
    }

    public void setTipoExcursionPreferida(String tipoExcursionPreferida) {
        this.tipoExcursionPreferida = tipoExcursionPreferida;
    }
}
