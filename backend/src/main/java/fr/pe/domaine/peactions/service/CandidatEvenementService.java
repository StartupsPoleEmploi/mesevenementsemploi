package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.*;
import fr.pe.domaine.peactions.repository.CandidatEvenementRepository;
import fr.pe.domaine.peactions.repository.EvenementRepository;
import fr.pe.domaine.peactions.repository.StatutInscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CandidatEvenementService {
    private static final Logger logger = LoggerFactory.getLogger(CandidatEvenementService.class);

    @Autowired
    private CandidatEvenementRepository repository;

    @Autowired
    private EvenementRepository evenementRepository;

    private static final int STATUT_INSCRIT = 0;
    private static final int STATUT_PREREQUIS_NON_VALIDE = 1;
    private static final int STATUT_EN_ATTENTE = 2;
    @Autowired
    private StatutInscriptionRepository statutInscriptionRepository;

    public void deleteCandidat(long candidatId, long evenementId) throws ResourceNotFoundException {
        CandidatEvenementKey candidatEvenementKey = new CandidatEvenementKey(candidatId, evenementId);

        CandidatEvenement candidat = repository.findById(candidatEvenementKey).
                orElseThrow(() -> new ResourceNotFoundException("Candidat not found for this id:: " + candidatEvenementKey));
        //TODO : lorsque les status seront implémentés, décommenter la ligne ci-dessous
        //candidat.setDateDesinscription(new Date());
        repository.delete(candidat);
    }

    public List<CandidatEvenement> getAllCandidatsByEvent(long eventIid, boolean avecPrerequisValider) throws ResourceNotFoundException {
        logger.info("getAllCandidatsByEvent for this id:: " + eventIid);
        Evenement evenement = evenementRepository.findById(eventIid).
                orElseThrow(() -> new ResourceNotFoundException("Evenement not found for this id:: " + eventIid));
        if (avecPrerequisValider) {
            StatutInscription statutInscription = this.statutInscriptionRepository.findById(STATUT_INSCRIT).
                    orElseThrow(() -> new ResourceNotFoundException("Statut not found for this id:: " + STATUT_INSCRIT));
            return repository.findAllByEvenementAndStatutInscription(evenement, statutInscription);
        } else {
            return repository.findAllByEvenement(evenement);
        }
    }

    public CandidatEvenement updateCandidatEvenement(CandidatEvenement candidatEvenement) {
        logger.info("Update candidat evenement...");
        CandidatEvenementKey candidatEvenementKey = new CandidatEvenementKey(candidatEvenement.getCandidat().getId(), candidatEvenement.getEvenement().getId());
        candidatEvenement.setId(candidatEvenementKey);
        return repository.save(candidatEvenement);
    }


    public CandidatEvenement getCandidatEvenement(CandidatEvenementKey key) {
        logger.info("get candidat evenement");
        CandidatEvenement candidat = repository.findById(key).
                orElseThrow(() -> new ResourceNotFoundException("Candidat not found for this id:: " + key));
        return candidat;
    }


    public CandidatEvenement createCandidatEvenement(CandidatEvenement candidatEvenement) {
        logger.info("service create candidat evenement");
        return repository.save(candidatEvenement);
    }

    public boolean isPresentCandidatEvenement(Candidat candidat, Evenement evenement) {
        logger.info("service get candidat evenement");
        Optional<CandidatEvenement> candidatEvenement = repository.findById(new CandidatEvenementKey(candidat, evenement));
        return candidatEvenement.isPresent();
    }

    public boolean isInscritCandidatEvenement(Candidat candidat, Evenement evenement) {
        logger.info("service get candidat evenement");
        Optional<CandidatEvenement> candidatEvenement = repository.findById(new CandidatEvenementKey(candidat, evenement));
        return candidatEvenement.isPresent() && candidatEvenement.get().isInscrit();
    }

    public boolean isCandidatInscritEvenement(long candidatId, long evenementId) {
        logger.info("service is candidat inscrit");
        Optional<CandidatEvenement> candidatEvenement = repository.findById(new CandidatEvenementKey(candidatId, evenementId));
        return candidatEvenement.isPresent() && candidatEvenement.get().isInscrit();
    }


    public List<CandidatEvenement> getAllInscrit(Evenement evenement, ModaliteAcces modaliteAcces) {
        StatutInscription statutInscription = this.statutInscriptionRepository.findById(STATUT_INSCRIT).
                orElseThrow(() -> new ResourceNotFoundException("Statut not found for this id:: " + STATUT_INSCRIT));
        return repository.findAllByEvenementAndAndModaliteAccesAndStatutInscription(evenement, modaliteAcces, statutInscription);
    }

    public Long countAllInscrit(Evenement evenement, ModaliteAcces modaliteAcces) {
        StatutInscription statutInscription = this.statutInscriptionRepository.findById(STATUT_INSCRIT).
                orElseThrow(() -> new ResourceNotFoundException("Statut not found for this id:: " + STATUT_INSCRIT));
        return repository.countAllByEvenementAndModaliteAccesAndStatutInscription(evenement, modaliteAcces, statutInscription);
    }

    public Long countAllPresent(Evenement evenement, ModaliteAcces modaliteAcces) {
        return repository.countAllByEvenementAndModaliteAccesAndPresentTrue(evenement, modaliteAcces);
    }
}
