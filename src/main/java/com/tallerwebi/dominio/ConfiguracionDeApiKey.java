package com.tallerwebi.dominio;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionDeApiKey {

    private final  String apiKey;

    public ConfiguracionDeApiKey(){

        this.apiKey = System.getenv("API_KEY");

    }

    public String getApiKey() {
        return apiKey;
    }
}
