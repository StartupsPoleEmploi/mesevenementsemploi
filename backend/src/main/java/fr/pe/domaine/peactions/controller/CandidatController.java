package fr.pe.domaine.peactions.controller;

import fr.pe.domaine.peactions.dto.CandidatDto;
import fr.pe.domaine.peactions.emploistoredev.ressources.DateNaissanceESD;
import fr.pe.domaine.peactions.exception.BadRequestException;
import fr.pe.domaine.peactions.exception.ExistingResourceExceprion;
import fr.pe.domaine.peactions.exception.FullEventException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.*;
import fr.pe.domaine.peactions.payload.Individu;
import fr.pe.domaine.peactions.payload.PeConnectPayload;
import fr.pe.domaine.peactions.service.*;
import fr.pe.domaine.peactions.utils.AccesTokenUtile;
import fr.pe.domaine.peactions.utils.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/candidat")
@CrossOrigin(origins = "*")
public class CandidatController {

    private static final Logger logger = LoggerFactory.getLogger(CandidatController.class);

    private CandidatEvenementService service;

    private CandidatService candidatService;


    private EvenementService evenementService;


    private MailService mailService;

    private ModaliteAccesService modaliteAccesService;

    private CustomMapper customMapper;


    private IndividuService individuService;

    private AccesTokenUtile accesTokenUtile;
    private StatutInscriptionService statutInscriptionService;

    @Autowired
    public CandidatController(CandidatEvenementService service, CandidatService candidatService, EvenementService evenementService, MailService mailService, ModaliteAccesService modaliteAccesService, StatutInscriptionService statutInscriptionService, CustomMapper customMapper, IndividuService individuService, AccesTokenUtile accesTokenUtile) {
        this.service = service;
        this.candidatService = candidatService;
        this.evenementService = evenementService;
        this.mailService = mailService;
        this.modaliteAccesService = modaliteAccesService;
        this.statutInscriptionService = statutInscriptionService;
        this.customMapper = customMapper;
        this.individuService = individuService;
        this.accesTokenUtile = accesTokenUtile;
    }

    @DeleteMapping("/evenement")
    public ResponseEntity deleteCandidat(@RequestParam(value = "candidatId") long candidatId, @RequestParam(value = "evenementId") long evenementId) {
        logger.info("Delete candidat...");
        service.deleteCandidat(candidatId, evenementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/evenement/{id}")
    public ResponseEntity<List<CandidatDto>> getAllCandidatsByEvent(@PathVariable(value = "id") long eventIid, @RequestParam(value = "avecPrerequisValider") boolean avecPrerequisValider) {
        List<CandidatEvenement> list = service.getAllCandidatsByEvent(eventIid, avecPrerequisValider);

        List<CandidatDto> candidatDtoList = new ArrayList<>();
        for (CandidatEvenement candidat : list) {
            candidatDtoList.add(customMapper.candidatEvenementtoCandidatDto(candidat));
        }
        return new ResponseEntity<List<CandidatDto>>(candidatDtoList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/count/inscrit")
    public ResponseEntity<Map<String, Object>> countAllInscrit(@RequestParam(value = "eventId") long eventId, @RequestParam(value = "modeacces") int modeacces) {
        Long count = 0l;
        Evenement evenement = evenementService.getEvenementById(eventId);
        ModaliteAcces modaliteAcces = modaliteAccesService.getModaliteAccesById(modeacces);
        if (evenement != null && modaliteAcces != null) {
            count = service.countAllInscrit(evenement, modaliteAcces);
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("count", count);
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/count/present")
    public ResponseEntity<Map<String, Object>> countAllPresent(@RequestParam(value = "eventId") long eventId, @RequestParam(value = "modeacces") int modeacces) {
        Long count = 0l;
        Evenement evenement = evenementService.getEvenementById(eventId);
        ModaliteAcces modaliteAcces = modaliteAccesService.getModaliteAccesById(modeacces);
        if (evenement != null && modaliteAcces != null) {
            count = service.countAllPresent(evenement, modaliteAcces);
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("count", count);
        return ResponseEntity.ok(payload);
    }


    @PutMapping("/evenement")
    public ResponseEntity<CandidatDto> updateCandidatEvenement(@RequestParam(value = "evenementId") long evenementId, @RequestBody CandidatDto candidatDto) {
        logger.info("Update candidat...");
        CandidatEvenement candidatEvenement = customMapper.candidatDtotoCandidatEvenement(evenementId, candidatDto);
        candidatEvenement = service.updateCandidatEvenement(candidatEvenement);
        if (candidatEvenement.isAnnulation()) {
            mailService.envoieMailAnnulation(candidatEvenement);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customMapper.candidatEvenementtoCandidatDto(candidatEvenement));
    }

    @PostMapping("/evenement")
    public CandidatDto inscrireCandidatEvenement(@RequestParam(value = "evenementId") long evenementId, @RequestBody CandidatDto candidatDto) throws FullEventException, BadRequestException {
        logger.info("inscrire candidat evenement...");
        Evenement evenement = evenementService.getEvenementById(evenementId);
        ModaliteAcces modaliteAcces = null;

        verifieeSiIndentifiantPresent(candidatDto);

        if (candidatDto.getModaliteAccesId() != null) {
            modaliteAcces = modaliteAccesService.getModaliteAccesById(candidatDto.getModaliteAccesId());
            if (evenement.complet(modaliteAcces)) {
                throw new FullEventException("plus de place disponible " + modaliteAcces.getLibelle() + " pour l'événement '" + evenement.getTitre() + "'");
            }
        }
        Candidat candidat = sauvegarderCandidat(candidatDto);

        if (isDejaIscrit(candidat, evenement)) {
            throw new ExistingResourceExceprion("Candidat déja inscrit");
        }

        CandidatEvenement candidatEvenement = sauvegardeCandidatEvenement(candidatDto, evenement, modaliteAcces, candidat);


        return customMapper.candidatEvenementtoCandidatDto(candidatEvenement);
    }

    private CandidatEvenement sauvegardeCandidatEvenement(CandidatDto candidatDto, Evenement evenement, ModaliteAcces modaliteAcces, Candidat candidat) {
        CandidatEvenement candidatEvenement = new CandidatEvenement(candidat, evenement, candidatDto.isPresent(), modaliteAcces);

            if (candidatDto.getStatutInscription().equals(Constantes.STATUT_INSCRIT) && evenement.encorePlaceDispo(modaliteAcces)) {
                candidatEvenement.setStatutInscription(this.statutInscriptionService.getStatutInscriptionById(Constantes.STATUT_INSCRIT));
            } else if (candidatDto.getStatutInscription().equals(Constantes.STATUT_INSCRIT) && !evenement.encorePlaceDispo(modaliteAcces)) {
                candidatEvenement.setStatutInscription(this.statutInscriptionService.getStatutInscriptionById(Constantes.STATUT_EN_ATTENTE));
            }else{
                candidatEvenement.setStatutInscription(this.statutInscriptionService.getStatutInscriptionById(candidatDto.getStatutInscription()));
            }

        candidatEvenement = service.createCandidatEvenement(candidatEvenement);

        if (candidatEvenement.isInscrit()) {
            mailService.envoieMailConfirmation(candidatEvenement);
        }
        return candidatEvenement;
    }

    private Candidat sauvegarderCandidat(CandidatDto candidatDto) {
        Candidat candidat = customMapper.map(candidatDto, Candidat.class);
        Candidat candidatBDD = candidatService.getCandidatsByIdentifiantCrypter(candidatDto.getIdentifiantCrypter());
        if (candidatBDD != null) {
            candidat.setId(candidatBDD.getId());
        }
        candidat = candidatService.createCandidat(candidat);
        return candidat;
    }


    private void verifieeSiIndentifiantPresent(CandidatDto candidatDto) {
        if (candidatDto.getIdentifiantCrypter() == null && candidatDto.getIdentifiantRci() == null) {
            throw new BadRequestException("l'identifiant du candidat ne doit pas être null");
        }
    }


    @PostMapping("/presence/evenement")
    public ResponseEntity<CandidatDto> presenceCandidatEvenement(@RequestParam(value = "evenementId") long evenementId, @RequestBody CandidatDto candidatDto) throws FullEventException {
        logger.info("declarer presence candidat evenement...");
        Evenement evenement = evenementService.getEvenementById(evenementId);
        Candidat candidat = candidatService.getCandidatById(candidatDto.getId());
        CandidatEvenement candidatEvenement = service.getCandidatEvenement(new CandidatEvenementKey(candidat, evenement));
        candidatEvenement.setPresent(candidatDto.isPresent());
        candidatEvenement = service.createCandidatEvenement(candidatEvenement);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customMapper.candidatEvenementtoCandidatDto(candidatEvenement));

    }


    private boolean isDejaIscrit(Candidat candidat, Evenement evenement) {
        return service.isInscritCandidatEvenement(candidat, evenement);
    }

    @GetMapping("/inscrit")
    private ResponseEntity<Map<String, Object>> isCandidatInscrit(@RequestParam(value = "candidatId") long candidatId, @RequestParam(value = "evenementId") long evenementId) {
        boolean inscrit = service.isCandidatInscritEvenement(candidatId, evenementId);
        Map<String, Object> payload = new HashMap<>();
        payload.put("inscrit", inscrit);
        return ResponseEntity.ok(payload);
    }

    @PostMapping("/authentifier")
    public ResponseEntity<CandidatDto> authentifierCandidat(@RequestBody PeConnectPayload peConnectPayload) throws FullEventException {
        logger.info("authentifier candidat ...");
        individuService.controlPayload(peConnectPayload);
        Individu individu = individuService.authentifier(
                peConnectPayload.getCode(),
                peConnectPayload.getRedirectURI(),
                peConnectPayload.getNonce());
        Candidat candidat = null;
        CandidatDto candidatDto = null;
        if (individu == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

            String accessToken = individu.getPeConnectAuthorization().getAccessToken();
            String bearerToken = accesTokenUtile.getBearerToken(accessToken);

            DateNaissanceESD dateNaissanceESD = individuService.getDateNaissance(bearerToken);

            candidat = customMapper.mapCandidat(individu, dateNaissanceESD);
            Candidat candidatBDD = candidatService.getCandidatsByIdentifiantCrypter(candidat.getIdentifiantCrypter());
            if (candidatBDD != null) {
                candidat.setId(candidatBDD.getId());
            }
            candidat = candidatService.createCandidat(candidat);
            candidatDto = customMapper.map(candidat, CandidatDto.class);
            candidatDto.setPeConnectAuthorization(individu.getPeConnectAuthorization());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(candidatDto);


    }


}
