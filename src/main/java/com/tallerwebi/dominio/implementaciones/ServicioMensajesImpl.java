package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioMensajes;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ServicioMensajesImpl implements ServicioMensajes {

    private final Dotenv dotenv = Dotenv.load();

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID = dotenv.get("ACCOUNT_SID");

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");

    @Value("${twilio.phone.number}")
    private String FROM_PHONE = dotenv.get("PHONE_NUMBER");

    @Autowired
    public void enviarSms(String numeroDestino, String mensaje) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + numeroDestino),
                new com.twilio.type.PhoneNumber(FROM_PHONE),
                mensaje
        ).create();
    }
}
