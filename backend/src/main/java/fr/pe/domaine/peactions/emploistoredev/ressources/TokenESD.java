package fr.pe.domaine.peactions.emploistoredev.ressources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class TokenESD {

    @JsonProperty("access_token")
    @Getter
    @Setter
    private String accessToken;


    @JsonProperty("expires_in")
    @Getter
    @Setter
    private Long expiresIn;

    @JsonProperty("id_token")
    @Getter
    @Setter
    private String idToken;

    @Getter
    @Setter
    private String nonce;

    @JsonProperty("refresh_token")
    @Getter
    @Setter
    private String refreshToken;

    @Getter
    @Setter
    private String scope;

    @JsonProperty("token_type")
    @Getter
    @Setter
    private String tokenType;


    @Override
    public String toString() {
        return "DonneesSortieAccessToken [scope=" + scope + ", expiresIn=" + expiresIn + ", tokenType=" + tokenType
                + ", accessToken=" + accessToken + ", idToken=" + idToken + ", refreshToken=" + refreshToken
                + ", nonce=" + nonce + "]";
    }
}
