package fr.pe.domaine.peactions.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class PeConnectAuthorizationESD {

    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;

    @Getter
    @Setter
    @JsonProperty("expires_in")
    private Long expiresIn;

    @Getter
    @Setter
    @JsonProperty("id_token")
    private String idToken;

    @Getter
    @Setter
    private String nonce;

    @Getter
    @Setter
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Getter
    @Setter
    private String scope;

    @Getter
    @Setter
    @JsonProperty("token_type")
    private String tokenType;


    @Override
    public String toString() {
        return "DonneesSortieAccessToken [scope=" + scope + ", expiresIn=" + expiresIn + ", tokenType=" + tokenType
                + ", accessToken=" + accessToken + ", idToken=" + idToken + ", refreshToken=" + refreshToken
                + ", nonce=" + nonce + "]";
    }
}
