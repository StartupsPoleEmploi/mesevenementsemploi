package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.commun.enumeration.ECategorieTag;
import fr.pe.domaine.peactions.commun.enumeration.EStatut;
import fr.pe.domaine.peactions.exception.InvalidEventException;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.*;
import fr.pe.domaine.peactions.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EvenementService {

    private static final Logger logger = LoggerFactory.getLogger(EvenementService.class);
    @Autowired
    private EvenementRepository repository;
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private EtablissementRepository etablissementRepository;
    @Autowired
    private EtatRepository etatRepository;
    @Autowired
    private CandidatEvenementRepository candidatEvenementRepository;
    @Autowired
    private PrerequisEvenementRepository prerequisEvenementRepository;
    @Autowired
    private EvenementModaliteAccesRepository modaliteAccesRepository;

    public Evenement getEvenementById(long evenementId) throws ResourceNotFoundException {
        Evenement evenement = repository.findById(evenementId).
                orElseThrow(() -> new ResourceNotFoundException("Evenement not found for this id:: " + evenementId));
        return evenement;
    }

    public void saveOffres(Evenement evenement) {
        logger.info("method saveOffres");
        if (evenement.getId() != null) {
            offreRepository.deleteAllByEvenement(evenement.getId());
        }
        if (evenement.getOffres() != null && !evenement.getOffres().isEmpty()) {
            List<Offre> offreList = offreRepository.saveAll(evenement.getOffres());
            evenement.setOffres(new HashSet<>(offreList));
        }
    }

    public void savePrerequis(Evenement evenement) {
        if (evenement.getId() != null) {
            prerequisEvenementRepository.deleteAllByEvenement(evenement);

            if (evenement.getPrerequisList() != null && !evenement.getPrerequisList().isEmpty()) {
                for (PrerequisEvenement prerequisEvenement : evenement.getPrerequisList()) {
                    Prerequis prerequis = prerequisEvenement.getPrerequis();
                    prerequisEvenement.setId(new PrerequisEvenementKey(prerequis, evenement));
                    prerequisEvenementRepository.save(prerequisEvenement);
                }

            }
        }
    }

    private void saveModalites(Evenement evenement) {
        logger.info("method saveModalites");
        if (evenement.getId() != null) {
            modaliteAccesRepository.deleteAllByEvenement(evenement);

        if (evenement.getEvenementModaliteAccesList() != null && !evenement.getEvenementModaliteAccesList().isEmpty()) {
            for (EvenementModaliteAcces evenementModaliteAcces : evenement.getEvenementModaliteAccesList()) {
                ModaliteAcces modaliteAcces = evenementModaliteAcces.getModaliteAcces();
                evenementModaliteAcces.setId(new EvenementModaliteAccesKey(modaliteAcces, evenement));

                modaliteAccesRepository.save(evenementModaliteAcces);
            }
        }
        }
    }

    @Transactional
    public Evenement createEvenement(Evenement evenement) throws ResourceNotFoundException, InvalidEventException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String codeEtab = userDetails.getUsername();

        Etablissement etablissement = etablissementRepository.findByCodeEtablissement(codeEtab).
                orElseThrow(() -> new ResourceNotFoundException("Etablissement not found for this code:: " + codeEtab));


        if (this.valid(evenement)) {
            if (evenement.getEtat().getId().equals(0l)) {
                evenement.setEstApublier(false);
            }
            if (evenement.getId() == null) {

                evenement.setEtablissement(etablissement);
                evenement.setCreatedBy(etablissement.getCodeEtablissement());
                saveOffres(evenement);
                evenement = repository.save(evenement);
                savePrerequis(evenement);
                saveModalites(evenement);
                return evenement;
            } else {
                Optional<Evenement> optEvtBDD = repository.findById(evenement.getId());
                if (optEvtBDD.isPresent()) {
                    evenement.setCreatedBy(optEvtBDD.get().getEtablissement().getCodeEtablissement());
                    evenement.setEtablissement(optEvtBDD.get().getEtablissement());
                    evenement.setUpdatedBy(etablissement.getCodeEtablissement());
                    evenement.setCreatedOn(optEvtBDD.get().getCreatedOn());
                    saveOffres(evenement);
                    savePrerequis(evenement);
                    saveModalites(evenement);
                    evenement = repository.save(evenement);

                    return evenement;
                }
            }
        }
        return null;
    }

    private boolean valid(Evenement evenement) {
        return evenement.getEtat().getId().equals(EStatut.BROUILLON.getId()) && this.validBrouillon(evenement) || (evenement.getEtat().getId().equals(EStatut.PRIVER.getId()) && this.validPublier(evenement));
    }

    private boolean validPublier(Evenement evenement) throws InvalidEventException {

        validBrouillon(evenement);

        if (isEmptyString(evenement.getDescription())) {
            throw new InvalidEventException("description est obligatoire");
        }

        if (evenement.getHeureDebut() == null) {
            throw new InvalidEventException("heure de debut est obligatoire");
        }
        if (evenement.getHeureFin() == null) {
            throw new InvalidEventException("heure de debut est obligatoire");
        }
        if (evenement.getHeureFin() == null) {
            throw new InvalidEventException("heure de fin est obligatoire");
        }
        if (evenement.getNafCode() == null) {
            throw new InvalidEventException("naf est obligatoire");
        }

        if (evenement.getRomeId() == null) {
            throw new InvalidEventException("rome est obligatoire");
        }

        if (evenement.getEvenementModaliteAccesList() == null || evenement.getEvenementModaliteAccesList().isEmpty()) {
            throw new InvalidEventException("au moins une modalalité acces obligatoire est obligatoire");
        } else {
            for (EvenementModaliteAcces evenementModaliteAcces : evenement.getEvenementModaliteAccesList()) {
                if (evenementModaliteAcces.getNombrePlace() == null) {
                    throw new InvalidEventException("nombre de pace obligatoire");
                }
            }
        }
        if (evenement.getTags() == null || evenement.getTags().isEmpty()) {
            throw new InvalidEventException("aucun tag");
        } else {
            validTags(evenement.getTags());
        }
        if (evenement.getPrerequisList() == null || evenement.getPrerequisList().isEmpty() || evenement.getPrerequisList().size() < 2) {
            throw new InvalidEventException("au moins 2 prerequis doivent etre selectionner");
        }
        return true;
    }

    private boolean validBrouillon(Evenement evenement) throws InvalidEventException {

        if (isEmptyString(evenement.getTitre())) {
            throw new InvalidEventException("le titre est obligatoire");
        }

        if (evenement.getDateEvenement() == null) {
            throw new InvalidEventException("la date est obligatoire");
        }

        return true;
    }

    private void validTags(Set<Tag> tags) throws InvalidEventException {
        List<Tag> tagsList = new ArrayList<>(tags);

        List<Tag> tagCaracteristiques = tagsList.stream()
                .filter(tag -> tag != null && tag.getTypeTag() != null && tag.getTypeTag().getId().equals(ECategorieTag.CARACTERISTIQUE_CIBLE.getId()))
                .collect(Collectors.toList());
        if (tagCaracteristiques == null || tagCaracteristiques.isEmpty()) {
            throw new InvalidEventException("au moins 1 caracteristique cible doivent etre selectionner");
        }


        List<Tag> tagType = tagsList.stream()
                .filter(tag -> tag != null && tag.getTypeTag() != null && tag.getTypeTag().getId().equals(ECategorieTag.TYPE.getId()))
                .collect(Collectors.toList());

        if (tagType == null || tagType.isEmpty() || tagType.size() != 1) {
            throw new InvalidEventException(" 1 type doit etre selectionner");
        }

        List<Tag> tagObjectif = tagsList.stream()
                .filter(tag -> tag != null && tag.getTypeTag() != null && tag.getTypeTag().getId().equals(ECategorieTag.OBJECTIF.getId()))
                .collect(Collectors.toList());

        if (tagObjectif == null || tagObjectif.isEmpty()) {
            throw new InvalidEventException(" au moins 1 objectif doit etre selectionner");
        }

    }

    boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }

    @Transactional
    public void deleteEvenement(long evenementId) throws ResourceNotFoundException {
        Evenement evenement = repository.findById(evenementId).
                orElseThrow(() -> new ResourceNotFoundException("Evenement not found for this id:: " + evenementId));
        if (evenement.getCandidatEvenementList() != null) {
            candidatEvenementRepository.deleteAllByEvenement(evenement);
        }
        if (evenement.getPrerequisList() != null) {
            prerequisEvenementRepository.deleteAllByEvenement(evenement);
        }
        if (evenement.getEvenementModaliteAccesList() != null) {
            modaliteAccesRepository.deleteAllByEvenement(evenement);
        }
        repository.delete(evenement);
    }

    public List<Evenement> getAllEvenements(String etat, String codeDepartement, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending().and(Sort.by("titre").ascending()));
        logger.info("getAllEvenements:: ");
        Page<Evenement> pagedResult = null;
        if (etat != null && etat.equalsIgnoreCase("publier")) {
            if (codeDepartement != null) {
                pagedResult = codeDepartement.equals("-1") ? repository.findAllAdistance(paging) : repository.findAllByDeptAndEstApublierTrue(codeDepartement, paging);
            } else {
                pagedResult = repository.findAllByEstApublierTrue(paging);
            }
        } else {
            pagedResult = repository.findAll(paging);
        }
        return  pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<Evenement>();
    }

    public List<Evenement> getAllEvenementsByEtablissementWithoutPaginate(Etablissement etablissement) {
        logger.info("getAllEvenementsByEtablissement for this id: {} ", etablissement);
        List<Evenement> evenements = repository.getAllByEtablissement(etablissement);
        return evenements;
    }

    public List<Evenement> getListEvenements() {
        List<Evenement> list = repository.findAll();
        return list;
    }

    public List<Evenement> getListEvenementsByEtablissement(Etablissement etablissement) {
        logger.info("getAllEvenementsByEtablissement for this id:: " + etablissement);
        List<Evenement> evenements = repository.getAllByEtablissement(etablissement);
        return evenements;
    }

    public List<Evenement> getAllEvenementsByEtablissement(Etablissement etablissement, Integer pageNo, Integer pageSize, String sortBy) {
        logger.info("getAllEvenementsByEtablissement for this id:: " + etablissement);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending().and(Sort.by("titre").ascending()));
        Page<Evenement> pagedResult = repository.findAllByEtablissement(etablissement, paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Evenement>();
        }
    }

    public List<Evenement> getAllEvenementsByDepartement(String codeDpt) {
        List<Evenement> evenements = repository.findByCodeEtablissementStartingWith(codeDpt);
        return evenements;
    }

    public Long getCountEvenementsByEtablissement(Etablissement etablissement) {
        logger.info("getCountEvenementsByEtablissement:: " + etablissement);
        return repository.countByEtablissement(etablissement);
    }

    public Long getCountAll(String etat,String dept) {
        logger.info("getCountAll:: ");
        if (etat == null) {
            return repository.count();
        }
        switch (etat) {
            case "publier":
                if (dept != null) {
                    return dept.equals("-1") ? repository.countAllADistance() : repository.countByDeptAndEstApublierTrue(dept);
                } else {
                    return repository.countByEstApublierTrue();
                }
            case "brouillon":
                Etat brouillon = etatRepository.findById(0l).
                        orElseThrow(() -> new ResourceNotFoundException("Etat not found for this id:: " + 0));
                return repository.countByEtat(brouillon);
            default:
                return repository.count();
        }

    }

    public List<Evenement> getListEvenementsPourRelanceVeille() {
        LocalDate demain = LocalDate.now().plusDays(1);
        Etat valide = etatRepository.findById(1l).
                orElseThrow(() -> new ResourceNotFoundException("Etat not found for this id:: " + 0));
        return repository.findAllByDateEvenementAndEstApublierTrueAndEtatOrderByDateEvenementAsc(demain, valide);
    }

    public List<Evenement> getBackEventsListOrderByStatus() {
        List<Evenement> inProgressEvents = repository.getAllByDateEvenementAfterNow();
        List<Evenement> doneEvents = repository.getEvenementByDateEvenementBeforeNow();
        ArrayList<Evenement> events = new ArrayList<>();
        events.addAll(inProgressEvents);
        events.addAll(doneEvents);
        return events;
    }

    public Page<Evenement> getBackEventsPagesOrderByStatus(Integer page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Evenement> events = getBackEventsListOrderByStatus();
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), events.size());

        Page<Evenement> pageOfEvents = new PageImpl<Evenement>(events.subList(start, end), pageable, events.size());
        return pageOfEvents;
    }

    public List<Evenement> getBackEventsListOrderByStatusAndByAgency(String codeEtablissement) {
        List<Evenement> inProgressEvents = repository.getByCodeEtablissementAllAndDateEvenementAfterNow(codeEtablissement);
        List<Evenement> doneEvents = repository.getByCodeEtablissementEvenementByDateEvenementBeforeNow(codeEtablissement);
        ArrayList<Evenement> events = new ArrayList<>();
        events.addAll(inProgressEvents);
        events.addAll(doneEvents);
        return events;
    }

    public Page<Evenement> getBackEventsPagesOrderByStatusAndByAgency(String codeEtablissement, Integer page, int size) {
        List<Evenement> eventsList = getBackEventsListOrderByStatusAndByAgency(codeEtablissement);
        Pageable pageable = PageRequest.of(page, size);
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), eventsList.size());
        Page<Evenement> pageOfEvents = new PageImpl<Evenement>(eventsList.subList(start, end), pageable, eventsList.size());
        return pageOfEvents;
    }

}
