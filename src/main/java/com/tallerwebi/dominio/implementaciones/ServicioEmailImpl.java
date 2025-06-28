package com.tallerwebi.dominio.implementaciones;
import com.tallerwebi.config.ConfiguracionDelMail;
import com.tallerwebi.dominio.ServicioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;

@Service
public class ServicioEmailImpl implements ServicioEmail {

    private final ConfiguracionDelMail config;
    private final String correoRemitente = System.getenv("CORREO_REMITENTE");
    private final String contraseña = System.getenv("CONTRASENA_CORREO");

    @Autowired
    public ServicioEmailImpl(ConfiguracionDelMail config) {
        this.config = config;
    }

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException {



        try {
            Message mensaje = new MimeMessage(config.getSession());
            mensaje.setFrom(new InternetAddress(config.getRemitente()));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            Transport.send(mensaje);

            System.out.println("Correo enviado con éxito.");
        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}