package fr.pe.domaine.peactions.payload;

import lombok.Getter;
import lombok.Setter;

public class PeConnectAuthorization {

    @Getter
    @Setter
    private String accessToken;

    @Getter
    @Setter
    private Long expireIn;

    @Getter
    @Setter
    private String idToken;

    @Getter
    @Setter
    private String refreshToken;

    @Getter
    @Setter
    private String scope;

    @Getter
    @Setter
    private String tokenType;


    @Override
    public String toString() {
        return "DonneesAccessToken [accessToken=" + accessToken + ", expireIn=" + expireIn + ", idToken=" + idToken
                + ", refreshToken=" + refreshToken + ", scope=" + scope + ", tokenType=" + tokenType + "]";
    }
}
