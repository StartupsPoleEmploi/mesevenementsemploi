package fr.pe.domaine.peactions.payload;

import lombok.Getter;
import lombok.Setter;

public class PeConnectPayload {

    @Getter
    @Setter
    private String clientId;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String nonce;

    @Getter
    @Setter
    private String redirectURI;

    @Getter
    @Setter
    private String state;


    @Override
    public String toString() {
        return "OIDCAuthorizeData [clientId=" + clientId + ", code=" + code + ", nonce=" + nonce + ", redirectURI="
                + redirectURI + ", state=" + state + "]";
    }
}
