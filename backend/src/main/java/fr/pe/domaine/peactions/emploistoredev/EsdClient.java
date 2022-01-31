package fr.pe.domaine.peactions.emploistoredev;

import fr.pe.domaine.peactions.commun.enumeration.InternalServerMessages;
import fr.pe.domaine.peactions.commun.enumeration.UnauthorizedMessages;
import fr.pe.domaine.peactions.emploistoredev.ressources.*;
import fr.pe.domaine.peactions.exception.UnauthorizedException;
import fr.pe.domaine.peactions.payload.PeConnectAuthorizationESD;
import fr.pe.domaine.peactions.utils.AccesTokenUtile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;
@Component
public class EsdClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(EsdClient.class);

    private final int errorTooManyRequestESD = 429;

    @Value("${esd.tokenentryPoint}")
    private String tokenESDEntryPoint;

    @Value("${esd.apiEntryPoint}")
    private String apiEntryPoint;

    @Value("${candidat.apiEntryPoint}")
    private String candidatEntryPoint;
    
    @Autowired
    private RestTemplateBuilder builder;

    @Autowired
    private EsdUtil emploiStoreDevUtile;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private AccesTokenUtile accesTokenUtile;

    @PostConstruct
    public void getRomeList() {

       // getRomes();
    }

    public TokenESD getTokenESD() {
        TokenESD tokenESD = null;

        try {
            final String url = tokenESDEntryPoint +
                    "/connexion/oauth2/access_token?realm=/partenaire";

            RestTemplate restTemplate = builder.build();
            final HttpEntity<MultiValueMap<String, String>> request =emploiStoreDevUtile.getAccessTokenMapRequestHttp();
            ResponseEntity<TokenESD> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST,
                            request, TokenESD.class);
            tokenESD = Objects.requireNonNull(responseEntity.getBody());;
        }  catch (Exception e) {
            logger.error("Erreur lors de la recuperation de l'access token",
                    e);
        }

        return tokenESD;

    }


    public PeConnectAuthorizationESD callAccessTokenEndPoint(String code, String redirectURI, String nonce) {
        PeConnectAuthorizationESD informationsAccessTokenESD = null;
        try {
            final String url = candidatEntryPoint +
                    "/connexion/oauth2/access_token?realm=/individu";

            RestTemplate restTemplate = builder.build();

            final HttpEntity<MultiValueMap<String, String>> request =emploiStoreDevUtile.getAccesTokenRequeteHTTP(code, redirectURI);

            //rest template : Post
            ResponseEntity<PeConnectAuthorizationESD> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST,
                            request, PeConnectAuthorizationESD.class);
            try {
                informationsAccessTokenESD = Objects.requireNonNull(responseEntity.getBody());
                if(informationsAccessTokenESD.getNonce().compareTo(nonce) != 0) {
                    logger.info(
                            UnauthorizedMessages.ACCES_NON_AUTORISE_NONCE_INCORRECT
                            .getMessage());
                    throw new UnauthorizedException(
                            InternalServerMessages.ACCES_APPLICATION_IMPOSSIBLE
                                    .getMessage());
                }
            } catch (Exception e) {
                logger.error("Erreur", e);
            }

        }  catch (Exception e) {
            logger.error("Erreur lors de la recuperation du code d'authorisation",
                    e);
        }

        return informationsAccessTokenESD;
    }

    public UserInfoESD callUserInfoEndPoint(String bearerToken) {
        UserInfoESD userInfos = new UserInfoESD();
         try {

             final String url = apiEntryPoint + "/peconnect-individu/v1/userinfo";
             RestTemplate restTemplate = builder.build();
             final HttpHeaders headers = new HttpHeaders();
             headers.add("Authorization", bearerToken);

             final HttpEntity<MultiValueMap<String, Object>> request =
                     new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

             ResponseEntity<UserInfoESD> responseEntity =
                     restTemplate
                             .exchange(url, HttpMethod.GET, request,
                                     UserInfoESD.class);

             userInfos = Objects.requireNonNull(responseEntity.getBody());
         } catch (Exception e) {
             logger.error("Erreur lors de l'appel de userinfo", e);
        }
        return userInfos;
    }

    public DateNaissanceESD callDateNaissanceEndPoint(String bearerToken) {
    	DateNaissanceESD dateNaissance = null;
        try {
            final String url = apiEntryPoint + "/peconnect-datenaissance/v1/etat-civil";
            RestTemplate restTemplate = builder.build();
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", bearerToken);

            final HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

            ResponseEntity<DateNaissanceESD> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, request, DateNaissanceESD.class);

            dateNaissance = Objects.requireNonNull(responseEntity.getBody());
        } catch (Exception e) {
        	logger.error("Erreur lors de l'appel de date naissance", e);
       }
        
        
        return dateNaissance;
    }

    @Cacheable(cacheNames = "romesList")
    public List<RomeESD> getRomes() {
        TokenESD tokenESD = getTokenESD();
        List<RomeESD> romeESDList = new ArrayList<>();
        romeESDList.addAll(Arrays.asList(getListeROMEMetier(tokenESD)));
        romeESDList.addAll(Arrays.asList(getListeROMEDomainePro(tokenESD)));
        logger.info("recuperation de  {} codes", romeESDList.size());
        return romeESDList;
    }

    public RomeESD[] getListeROMEMetier(TokenESD tokenESD) {
        RomeESD[] romesMetier = null;

        try {
            final String url = apiEntryPoint + "/rome/v1/metier";
            RestTemplate restTemplate = builder.build();
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",
                    accesTokenUtile.getBearerToken(tokenESD.getAccessToken()));

            final HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

            ResponseEntity<RomeESD[]> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET,
                            request, RomeESD[].class);

            if(responseEntity.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                Thread.sleep(2000);
                logger.info("Erreur 429");
                responseEntity =
                        restTemplate.exchange(url, HttpMethod.GET,
                                request, RomeESD[].class);
            }

            romesMetier = Objects.requireNonNull(responseEntity.getBody());

        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation de la liste des metiers", e);
        }

        return romesMetier;

    }

    public RomeESD[] getListeROMEDomainePro(TokenESD tokenESD) {

        RomeESD[] romesDomainePro = null;

        try {
            final String url = apiEntryPoint + "/rome/v1/domaineprofessionnel";
            RestTemplate restTemplate = builder.build();
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",
                    accesTokenUtile.getBearerToken(tokenESD.getAccessToken()));

            final HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

            ResponseEntity<RomeESD[]> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET,
                            request, RomeESD[].class);

            if(responseEntity.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                Thread.sleep(2000);
                logger.info("Erreur 429");
                responseEntity =
                        restTemplate.exchange(url, HttpMethod.GET,
                                request, RomeESD[].class);
            }

            romesDomainePro = Objects.requireNonNull(responseEntity.getBody());


        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation de la liste des domaines professionnel", e);
        }

        return romesDomainePro;
    }


    public RomeESD getRome(String code) {

        Cache cache = cacheManager.getCache("romesList");
        if (cache != null && !getRomes().isEmpty()) {
            return getRomes().stream().filter(romeESD ->
                    romeESD.getCode().equals(code)).findFirst().orElse(null);
        } else {
            return recupererRomeDepuisESD(code);
        }
    }


    private RomeESD recupererRomeDepuisESD(String code) {
        RomeESD rome = null;
        if (code != null) {
            final String token = getTokenESD().getAccessToken();
            String path;
            if (code.length() == 3) {
                path = "/rome/v1/domaineprofessionnel/";
            } else {
                path = "/rome/v1/metier/";
            }

            try {
                final String url = apiEntryPoint + path + code;
                RestTemplate restTemplate = builder.build();
                final HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization",
                        accesTokenUtile
                                .getBearerToken(token));

                final HttpEntity<MultiValueMap<String, Object>> request =
                        new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

                ResponseEntity<RomeESD> responseEntity =
                        restTemplate.exchange(url, HttpMethod.GET,
                                request, RomeESD.class);

                if(responseEntity.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS)) {
                    Thread.sleep(2000);
                    logger.info("Erreur 429");
                    responseEntity =
                            restTemplate.exchange(url, HttpMethod.GET,
                                    request, RomeESD.class);
                }

                rome = Objects.requireNonNull(responseEntity.getBody());
            } catch (Exception e) {
                logger.error("Erreur lors de la recuperation du rome par code",
                        e);
            }
        }

            return rome;
    }

    public OffreESD getOffre(String identifiant) {

        OffreESD offres = null;
        final String token = getTokenESD().getAccessToken();

        try {
            final String url = apiEntryPoint + "/offresdemploi/v2/offres/"
                    + identifiant;
            RestTemplate restTemplate = builder.build();
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",
                    accesTokenUtile.getBearerToken(token));

            final HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

            ResponseEntity<OffreESD> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET,
                            request, OffreESD.class);

            offres = Objects.requireNonNull(responseEntity.getBody());
        } catch (Exception e) {
            logger.error("Erreur lors de la recuperation de l'offre", e);
        }

        return offres;

    }


}
