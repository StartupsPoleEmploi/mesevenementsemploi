package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.emploistoredev.EsdClient;
import fr.pe.domaine.peactions.emploistoredev.ressources.DateNaissanceESD;
import fr.pe.domaine.peactions.emploistoredev.ressources.UserInfoESD;
import fr.pe.domaine.peactions.exception.BadRequestException;
import fr.pe.domaine.peactions.payload.Individu;
import fr.pe.domaine.peactions.payload.PeConnectAuthorizationESD;
import fr.pe.domaine.peactions.payload.PeConnectPayload;
import fr.pe.domaine.peactions.utils.AccesTokenUtile;
import fr.pe.domaine.peactions.utils.PeConnectUtile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
public class IndividuService {

    private static final Logger logger = LoggerFactory.getLogger(IndividuService.class);

    private final String CHAMP_OBLIGATOIRE = "Le champ %s est obligatoire";

    @Autowired
    private PeConnectUtile peConnectUtile;

    @Autowired
    private AccesTokenUtile accesTokenUtile;
    private final String INDIVIDU_OBLIGATOIRE = "Des informations de l'individu sont obligatoires.";
    @Autowired
    private EsdClient esdClient;

    public void controlPayload(PeConnectPayload peConnectPayload) {
        if (peConnectPayload == null) {
            throw new BadRequestException(INDIVIDU_OBLIGATOIRE);
        } else {
            if (StringUtils.isEmpty(peConnectPayload.getCode())) {
                throw new BadRequestException(String.format(CHAMP_OBLIGATOIRE, "code de peConnectPayload"));
            }
            if (StringUtils.isEmpty(peConnectPayload.getRedirectURI())) {
                throw new BadRequestException(String.format(CHAMP_OBLIGATOIRE, "redirectURI de peConnectPayload"));
            }
            if (StringUtils.isEmpty(peConnectPayload.getNonce())) {
                throw new BadRequestException(String.format(CHAMP_OBLIGATOIRE, "nonce de peConnectPayload"));
            }
        }
    }

    public Individu authentifier(String code, String redirectURI, String nonce) {

        Individu individu = null;
        PeConnectAuthorizationESD peConnectAuthorizationESD = esdClient.callAccessTokenEndPoint(code, redirectURI, nonce);
        if (peConnectAuthorizationESD != null) {
            individu = new Individu();
            String bearerToken = accesTokenUtile.getBearerToken(peConnectAuthorizationESD.getAccessToken());
            //Mono<UserInfoESD> mono = esdClient.callUserInfoEndPoint(bearerToken);
            //UserInfoESD userInfoESD = mono.block();
            UserInfoESD userInfoESD = esdClient.callUserInfoEndPoint(bearerToken);
            logger.info("Info client récupérées : " + userInfoESD.toString());

            individu.setUserInfoESD(userInfoESD);
            individu.setPeConnectAuthorization(peConnectUtile.mapInformationsAccessTokenPeConnect(peConnectAuthorizationESD));
            return individu;
        }
        return null;
    }

    public DateNaissanceESD getDateNaissance(String bearerToken) {
        return esdClient.callDateNaissanceEndPoint(bearerToken);
    }
}
