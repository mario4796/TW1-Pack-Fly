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
        System.out.println("Twilio autenticado con SID: " + ACCOUNT_SID);
    }

    @Override
    public void enviarMensaje(String telefonoDestino, String mensaje) {
        String telefonoFormateado = normalizarTelefono(telefonoDestino);
        Message.creator(
                new PhoneNumber("whatsapp:" + telefonoFormateado),
                new PhoneNumber(FROM_WHATSAPP_NUMBER),
                mensaje
        ).create();

        System.out.println("✅ Mensaje enviado (o en proceso)");

    }

    private String normalizarTelefono(String telefono) {
        // Eliminar espacios, guiones, paréntesis, etc.
        telefono = telefono.replaceAll("[^\\d]", "");

        // Si ya empieza con 549 o 54, no volver a agregarlo
        if (telefono.startsWith("549")) {
            return "+" + telefono;
        } else if (telefono.startsWith("54")) {
            return "+" + telefono;
        }

        // Si empieza con 0 (prefijo nacional), lo reemplazamos por 549
        if (telefono.startsWith("0")) {
            telefono = telefono.replaceFirst("0", "");
        }

        return "+549" + telefono;
    }
}
