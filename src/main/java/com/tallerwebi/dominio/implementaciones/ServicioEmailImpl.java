package com.tallerwebi.dominio.implementaciones;
import com.tallerwebi.config.ConfiguracionDelMail;
import com.tallerwebi.dominio.ServicioEmail;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.*;


@Service
public class ServicioEmailImpl implements ServicioEmail {
    Dotenv dotenv = Dotenv.load();
    private final ConfiguracionDelMail config;
    private final String correoRemitente = dotenv.get("CORREO_REMITENTE");
    private final String contraseña = dotenv.get("CONTRASENA_CORREO");

    @Autowired
    public ServicioEmailImpl(ConfiguracionDelMail config) {
        this.config = config;
    }

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException{



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