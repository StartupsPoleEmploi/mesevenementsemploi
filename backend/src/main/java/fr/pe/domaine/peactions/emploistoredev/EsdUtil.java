package fr.pe.domaine.peactions.emploistoredev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.io.Serializable;

@Component
public class EsdUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String grantType = "client_credentials";
    private final String authorizationGrantType = "authorization_code";

    @Value("${api.rome.scope}")
    private String scopesRome;

    @Value("${api.offre.scope}")
    private String scopesOffre;

    @Value("${api.datenaissance.scope}")
    private String scopesDateDeNaissance;

    @Value("${esd.clientId}")
    private String clientId;

    @Value("${esd.clientSecret}")
    private String clientSecret;

    @Value("${esd.redirectURI}")
    private String redirectURI;


    public HttpEntity<MultiValueMap<String, String>> getAccesTokenRequeteHTTP(String code, String redirectURI) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("redirect_uri", redirectURI);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", authorizationGrantType);

        return new HttpEntity<>(map, headers);
    }

    public HttpEntity<MultiValueMap<String, String>> getAccessTokenMapRequestHttp() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", this.clientId);
        map.add("client_secret", this.clientSecret);
        map.add("grant_type", this.grantType);
        map.add("scope", this.scopesOffre + " " + this.scopesRome
                + " " + "application_" + this.clientId);

        return new HttpEntity<>(map, headers);
    }

}
