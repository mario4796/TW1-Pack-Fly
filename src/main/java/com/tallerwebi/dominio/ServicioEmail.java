package com.tallerwebi.dominio;

public interface ServicioEmail {

    void enviarCorreoSimple(String destinatario, String asunto, String mensaje);
}
