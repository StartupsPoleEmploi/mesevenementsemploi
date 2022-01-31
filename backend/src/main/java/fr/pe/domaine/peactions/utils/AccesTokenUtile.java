package fr.pe.domaine.peactions.utils;

import org.springframework.stereotype.Component;

@Component
public class AccesTokenUtile {

    private static final String BEARER = "Bearer ";

    public String getBearerToken(String accessToken) {
        return BEARER + accessToken;
    }
}
