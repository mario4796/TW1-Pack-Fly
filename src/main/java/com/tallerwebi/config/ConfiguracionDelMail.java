package com.tallerwebi.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class ConfiguracionDelMail {

    private final Dotenv dotenv = Dotenv.load();

    public Session getSession() {
        final String username = dotenv.get("MAIL_USERNAME");
        final String password = dotenv.get("MAIL_PASSWORD");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public String getRemitente() {
        return dotenv.get("MAIL_USERNAME");
    }
}