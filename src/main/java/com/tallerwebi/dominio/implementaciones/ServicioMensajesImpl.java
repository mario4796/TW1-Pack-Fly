package com.tallerwebi.dominio.implementaciones;

import com.tallerwebi.dominio.ServicioMensajes;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

@Service
public class ServicioMensajesImpl implements ServicioMensajes {


    private static final Dotenv dotenv = Dotenv.load();
    private static final String ACCOUNT_SID = dotenv.get("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = dotenv.get("TWILIO_AUTH_TOKEN");
    private static final String FROM_WHATSAPP_NUMBER = dotenv.get("TWILIO_FROM_NUMBER");

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public void enviarMensaje(String telefonoDestino, String mensaje) {
        String telefonoFormateado = normalizarTelefono(telefonoDestino);
        Message.creator(
                new PhoneNumber(telefonoFormateado),
                new PhoneNumber(FROM_WHATSAPP_NUMBER),
                mensaje
        ).create();

        System.out.println("✅ Mensaje enviado (o en proceso)");

    }

    private String normalizarTelefono(String telefono) {
        telefono = telefono.replaceAll("[^\\d]", "");
        if (telefono.startsWith("54")) {
            return "whatsapp:+" + telefono;
        } else if (telefono.startsWith("0")) {
            return telefono.replaceFirst("0", "whatsapp:+549");
        } else {
            return "whatsapp:+549" + telefono; // fallback
        }
    }
}
