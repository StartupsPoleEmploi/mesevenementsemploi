package fr.pe.domaine.peactions.utils;

import fr.pe.domaine.peactions.payload.PeConnectAuthorization;
import fr.pe.domaine.peactions.payload.PeConnectAuthorizationESD;
import org.springframework.stereotype.Component;

@Component
public class PeConnectUtile {

    public PeConnectAuthorization mapInformationsAccessTokenPeConnect(PeConnectAuthorizationESD peConnectAuthorizationESD) {
        PeConnectAuthorization peConnectAuthorization = new PeConnectAuthorization();
        peConnectAuthorization.setAccessToken(peConnectAuthorizationESD.getAccessToken());
        peConnectAuthorization.setExpireIn(peConnectAuthorizationESD.getExpiresIn());
        peConnectAuthorization.setIdToken(peConnectAuthorizationESD.getIdToken());
        peConnectAuthorization.setRefreshToken(peConnectAuthorizationESD.getRefreshToken());
        peConnectAuthorization.setScope(peConnectAuthorizationESD.getScope());
        peConnectAuthorization.setTokenType(peConnectAuthorizationESD.getTokenType());
        return peConnectAuthorization;
    }
}
