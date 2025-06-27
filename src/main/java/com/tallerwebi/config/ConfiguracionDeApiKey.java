package com.tallerwebi.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionDeApiKey {

    private final  String apiKey;

    public ConfiguracionDeApiKey(){
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("API_KEY");

    }

    public String getApiKey() {
        return apiKey;
    }
}
