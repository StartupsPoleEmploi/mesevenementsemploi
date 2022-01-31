package fr.pe.domaine.peactions.mapper;

import fr.pe.domaine.peactions.dto.*;
import fr.pe.domaine.peactions.emploistoredev.ressources.DateNaissanceESD;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.*;
import fr.pe.domaine.peactions.payload.Individu;
import fr.pe.domaine.peactions.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class CustomMapper extends ModelMapper {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(CustomMapper.class);
@Autowired
private EvenementService evenementService;

    @Autowired
    private TagService tagService;
    @Autowired
    private PrerequisService prerequisService;
    @Autowired
    private ModaliteAccesService modaliteAccesService;
    @Autowired
    private EtatService etatService;

    @Autowired
    private NafService nafService;

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private StatutInscriptionService statutInscriptionService;

    public Evenement evenementDtoToEvenement(EvenementDto dto) throws ResourceNotFoundException {
        logger.info("evenementDtoToEvenement..." + dto);
        Evenement evenement = this.map(dto, Evenement.class);

        evenement.setEvenementModaliteAccesList(new ArrayList<>());
        evenement.setOffres(new HashSet<>());
        evenement.setPrerequisList(new HashSet<>());
        evenement.setTags(new HashSet<>());
        evenement.setIntervenants(new ArrayList<>());
        evenement.setEtat(etatService.getEtatById(dto.getEtatId()));
        Naf naf = nafService.getNafByCode(dto.getNafCode());
        if (naf != null) {
            evenement.setNafCode(naf.getCode());
        }
        handleModeAcces(dto, evenement);
        handleOffres(dto, evenement);
        handleIntervenants(dto, evenement);
        handlePrerequis(dto, evenement);
        handleTags(dto, evenement);
        return evenement;
    }
    
    private void handleModeAcces(EvenementDto dto, Evenement evenement) {
    	if (dto.getModeAcceesList() != null) {
            for (ModeAccees modeAccees : dto.getModeAcceesList()) {
                EvenementModaliteAcces evenementModaliteAcces = new EvenementModaliteAcces(modaliteAccesDtoDtoToModaliteAcces(modeAccees.getModaliteAcces()), evenement, modeAccees.getNombrePlace(), modeAccees.getUrlParticipation());
                evenement.getEvenementModaliteAccesList().add(evenementModaliteAcces);
            }
        }
    }
    
    private void handleOffres(EvenementDto dto, Evenement evenement) {
    	if (dto.getOffres() != null) {
            for (OffreDto offreDto : dto.getOffres()) {
                evenement.getOffres().add(map(offreDto, Offre.class));
            }
        }
    }
    
    private void handleIntervenants(EvenementDto dto, Evenement evenement) {
    	if (dto.getIntervenants() != null) {
            for (IntervenantDto intervenantDto : dto.getIntervenants()) {
                evenement.getIntervenants().add(intervenantDtoToIntervenant(intervenantDto, evenement));
            }
        }
    }
    
    private void handlePrerequis(EvenementDto dto, Evenement evenement) {
    	if (dto.getPrerequis() != null) {
            for (PrerequisDto prerequisDto : dto.getPrerequis()) {
                Prerequis prerequis = prerequisDtoToPrerequis(prerequisDto);
                evenement.getPrerequisList().add(new PrerequisEvenement(prerequis, evenement));
            }
        }
    }
    
    private void handleTags(EvenementDto dto, Evenement evenement) {
    	if (dto.getTags() != null) {
            for (TagDto tagDto : dto.getTags()) {
                evenement.getTags().add(tagDtoToTag(tagDto));
            }
        }
    }

    private ModaliteAcces modaliteAccesDtoDtoToModaliteAcces(ModaliteAccesDto modaliteAccesDto) throws ResourceNotFoundException {
        return modaliteAccesService.getModaliteAccesById(modaliteAccesDto.getId());

    }

    private Prerequis prerequisDtoToPrerequis(PrerequisDto prerequisDto) throws ResourceNotFoundException {
        return prerequisService.getPrerequisById(prerequisDto.getId());
    }

    private Tag tagDtoToTag(TagDto tagDto) throws ResourceNotFoundException {
        if (tagDto != null && tagDto.getId() != null) {
            return tagService.getTagById(tagDto.getId());
        }
        return null;
    }

    private Candidat candidatDtoToCandodat(CandidatDto candidatDto) {
        return map(candidatDto, Candidat.class);
    }


    private Intervenant intervenantDtoToIntervenant(IntervenantDto intervenantDto, Evenement evenement) {
        Intervenant intervenant = map(intervenantDto, Intervenant.class);
        intervenant.setEvenement(evenement);
        return intervenant;
    }


    public EvenementDto evenementToEvenementDto(Evenement evenement) {

        this.getConfiguration().setAmbiguityIgnored(true);
        EvenementDto evenementDto = this.map(evenement, EvenementDto.class);
        evenementDto.setTags(new ArrayList<>());
        evenementDto.setOffres(new ArrayList<>());
        evenementDto.setPrerequis(new ArrayList<>());
        evenementDto.setModeAcceesList(new ArrayList<>());
        evenementDto.setEtatId(evenement.getEtat().getId());
        evenementDto.setIntervenants(new ArrayList<>());
        evenementDto.setStatut(evenement.getStatut());
        mapNaf(evenement, evenementDto);
        evenementDto.setRomeId(evenement.getRomeId());

        evenementDto.setDateISO(this.convertToDateViaInstant(evenement.getDateEvenement()));

        mapTags(evenement, evenementDto);
        mapOffres(evenement, evenementDto);
        mapIntervenants(evenement, evenementDto);
        mapPrerequis(evenement, evenementDto);

        mapModalitesAcces(evenement, evenementDto);

        evenementDto.setStatut(evenement.getStatut());

        if (evenement.getEtablissement() != null) {
            evenementDto.setCodeEtablissement(evenement.getEtablissement().getCodeEtablissement());
            evenementDto.setLibelleEtablissement(evenement.getEtablissement().getLibelle());
        }

        return evenementDto;
    }

    private void mapModalitesAcces(Evenement evenement, EvenementDto evenementDto) {
        if (evenement.getEvenementModaliteAccesList() != null && !evenement.getEvenementModaliteAccesList().isEmpty()) {
            for (EvenementModaliteAcces evenementModaliteAcces : evenement.getEvenementModaliteAccesList()) {
                if (evenementModaliteAcces != null) {
                    ModaliteAcces modaliteAcces = evenementModaliteAcces.getModaliteAcces();
                    evenementDto.getModeAcceesList().add(new ModeAccees(modaliteAcces.getId(), modaliteAcces.getLibelle(), evenement.nbInscrit(modaliteAcces), evenementModaliteAcces.getNombrePlace(), evenement.nbPresent(modaliteAcces), evenementModaliteAcces.getUrlAcces()));
                }
            }
        }
    }

    private void mapPrerequis(Evenement evenement, EvenementDto evenementDto) {
        if (evenement.getPrerequisList() != null && !evenement.getPrerequisList().isEmpty()) {
            for (PrerequisEvenement prerequisEvenement : evenement.getPrerequisList()) {
                if (prerequisEvenement != null) {
                    evenementDto.getPrerequis().add(new PrerequisDto(prerequisEvenement.getPrerequis().getId(), prerequisEvenement.getPrerequis().getCategorie().getId(), prerequisEvenement.getPrerequis().getLibelle()));
                }
            }
        }
    }

    private void mapIntervenants(Evenement evenement, EvenementDto evenementDto) {
        if (evenement.getIntervenants() != null && !evenement.getIntervenants().isEmpty()) {
            for (Intervenant intervenant : evenement.getIntervenants()) {
                if (intervenant != null) {
                    evenementDto.getIntervenants().add(this.map(intervenant, IntervenantDto.class));
                }
            }
        }
    }

    private void mapOffres(Evenement evenement, EvenementDto evenementDto) {
        if (evenement.getOffres() != null && !evenement.getOffres().isEmpty()) {
            for (Offre offre : evenement.getOffres()) {
                if (offre != null) {
                    evenementDto.getOffres().add(new OffreDto(offre.getId()));
                }
            }
        }
    }

    private void mapTags(Evenement evenement, EvenementDto evenementDto) {
        if (evenement.getTags() != null && !evenement.getTags().isEmpty()) {
            for (Tag tag : evenement.getTags()) {
                if (tag != null) {
                    evenementDto.getTags().add(new TagDto(tag.getId(), tag.getLibelle(), tag.getTypeTag().getId()));
                }
            }
        }
    }


    private void mapNaf(Evenement evenement, EvenementDto evenementDto) {
        Naf naf = nafService.getNafByCode(evenement.getNafCode());
        if (naf != null) {
            evenementDto.setNafLibelle(naf.getLibelle());
            evenementDto.setNafCode(naf.getCode());
        }
    }


    public EtablissementDto etablissementToEtablissementDto(Etablissement etablissement) {
        EtablissementDto dto = this.map(etablissement, EtablissementDto.class);
        return dto;
    }

    public TagDto tagToTagDto(Tag tag) {
        TagDto dto = this.map(tag, TagDto.class);
        dto.setTypeTagId(tag.getTypeTag().getId());
        return dto;
    }


    public PrerequisDto prerequisToPrerequisDto(Prerequis prerequis) {
        PrerequisDto dto = this.map(prerequis, PrerequisDto.class);
        dto.setCategorieId(prerequis.getCategorie().getId());
        dto.setLibelle(prerequis.getLibelle());
        return dto;
    }

    public CandidatDto candidatEvenementtoCandidatDto(CandidatEvenement candidatEvenement) {
        CandidatDto dto = this.map(candidatEvenement.getCandidat(), CandidatDto.class);

        dto.setStatutInscription(candidatEvenement.getStatutInscription().getId());
        dto.setPresent(candidatEvenement.isPresent());

        if (candidatEvenement.getModaliteAcces() != null) {
            dto.setModaliteAccesId(candidatEvenement.getModaliteAcces().getId());
        }
        return dto;
    }

    public CandidatEvenement candidatDtotoCandidatEvenement(Long evenementId, CandidatDto candidatDto) throws ResourceNotFoundException {
        if(evenementId==null || candidatDto.getId()==null){
            throw new ResourceNotFoundException("");
        }
        CandidatEvenementKey candidatEvenementKey = new CandidatEvenementKey(evenementId, candidatDto.getId());
        CandidatEvenement candidatEvenement = new CandidatEvenement();

        candidatEvenement.setStatutInscription(this.statutInscriptionService.getStatutInscriptionById( candidatDto.getStatutInscription()));

        candidatEvenement.setPresent(candidatDto.isPresent());
        candidatEvenement.setId(candidatEvenementKey);
        candidatEvenement.setCandidat(candidatService.getCandidatById(candidatDto.getId()));
        candidatEvenement.setEvenement(evenementService.getEvenementById(evenementId));
        if (candidatDto.getModaliteAccesId() != null) {
            candidatEvenement.setModaliteAcces(modaliteAccesService.getModaliteAccesById(candidatDto.getModaliteAccesId()));
        }
        return candidatEvenement;
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        Date date = Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public Candidat mapCandidat(Individu individu, DateNaissanceESD dateNaissanceESD) {
        Candidat candidat = new Candidat();
        candidat.setCivilite(individu.getUserInfoESD().getGender());
        candidat.setNom(individu.getUserInfoESD().getFamilyName());
        candidat.setPrenom(individu.getUserInfoESD().getGivenName());
        candidat.setEmail(individu.getUserInfoESD().getEmail());
        candidat.setIdentifiantCrypter(individu.getUserInfoESD().getSub());
        candidat.setDateNaissance(dateNaissanceESD.getDateDeNaissance());

        return candidat;
    }
}
