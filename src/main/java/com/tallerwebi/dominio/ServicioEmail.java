package com.tallerwebi.dominio;

import javax.mail.MessagingException;

public interface ServicioEmail {
    void enviarCorreo(String destino, String asunto, String cuerpo) throws MessagingException;
}
