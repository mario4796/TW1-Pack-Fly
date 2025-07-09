package com.tallerwebi.dominio;

import jakarta.mail.MessagingException;

public interface ServicioEmail {
    void enviarCorreo(String destino, String asunto, String cuerpo) throws MessagingException;
    void enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpo, byte[] pdfBytes, String nombreArchivo) throws MessagingException;

}