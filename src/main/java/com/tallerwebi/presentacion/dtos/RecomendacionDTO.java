package com.tallerwebi.presentacion.dtos;

public class RecomendacionDTO {

    private String tipo;
    private String titulo;
    private String imagenUrl;

    public RecomendacionDTO(){
    }

    public RecomendacionDTO(String tipo, String titulo, String imagenUrl) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }
}