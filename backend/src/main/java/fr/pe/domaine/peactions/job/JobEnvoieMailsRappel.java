package fr.pe.domaine.peactions.job;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.CandidatEvenement;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.StatutInscription;
import fr.pe.domaine.peactions.repository.CandidatEvenementRepository;
import fr.pe.domaine.peactions.repository.StatutInscriptionRepository;
import fr.pe.domaine.peactions.service.EvenementService;
import fr.pe.domaine.peactions.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobEnvoieMailsRappel {

    @Autowired
    private EvenementService evenementService;

    @Autowired
    private MailService mailService;

    @Autowired
    private CandidatEvenementRepository candidatEvenementRepository;

    private static final int STATUT_INSCRIT = 0;
    @Autowired
    private StatutInscriptionRepository statutInscriptionRepository;

    // tous les jour à 10H00
    @Scheduled(cron = "0 0 10 ? * *")
    public void rappelVeilleEvt() {
        StatutInscription statutInscription = this.statutInscriptionRepository.findById(STATUT_INSCRIT).
                orElseThrow(() -> new ResourceNotFoundException("Statut not found for this id:: " + STATUT_INSCRIT));

        List<Evenement> evenements = evenementService.getListEvenementsPourRelanceVeille();
        evenements.forEach(evenement -> {
            List<CandidatEvenement> candidatEvenementList = candidatEvenementRepository.findAllByEvenementAndStatutInscription(evenement, statutInscription);
            candidatEvenementList.forEach(candidatEvenement -> {
                candidatEvenement.setEvenement(evenement);
                mailService.envoieMailRappelVeille(candidatEvenement);
            });
        });
    }

    //0 0/30 8,15 * * MON-FRI
//    @Scheduled(cron = "0 0/30 * * * MON-FRI")
//    public void rappeltouteles30min() {
//        List<Evenement> evenements = evenementService.getListEvenementsPourRelance30min();
//        evenements.forEach(evenement -> {
//            List<CandidatEvenement> candidatEvenementList = candidatEvenementRepository.findAllByEvenementAndPrerequisValiderTrueAndInscritTrue(evenement);
//            candidatEvenementList.forEach(candidatEvenement -> {
//                candidatEvenement.setEvenement(evenement);
//                mailService.envoieMailRappel30min(candidatEvenement);
//            });
//        });
//    }
}
