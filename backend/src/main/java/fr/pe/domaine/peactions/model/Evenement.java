package fr.pe.domaine.peactions.model;

import fr.pe.domaine.peactions.commun.enumeration.ECategorieTag;
import fr.pe.domaine.peactions.commun.enumeration.EModaliteAcces;
import fr.pe.domaine.peactions.commun.enumeration.EStatut;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Audited
@Data
@Entity
@NoArgsConstructor
@Table(name = "evenement")
@ToString(of = {"id", "titre"})
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evenement_generator")
    @SequenceGenerator(name = "evenement_generator", sequenceName = "evenement_seq", allocationSize = 1)
    @Column(name = "evenement_id")
    @Getter
    @Setter
    private Long id;

    @NotAudited
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "code_etablissement",
            referencedColumnName = "code_etablissement"
    )
    private Etablissement etablissement;

    @Column(name = "titre")
    @Size(max = 300)
    @Getter
    @Setter
    private String titre;

    @Column(name = "adresse")
    @Getter
    @Setter
    private String adresse;

    @Column(name = "code_postal")
    @Getter
    @Setter
    private String codePostal;

    @Column(name = "ville")
    @Getter
    @Setter
    private String ville;

    @Column(name = "description")
    @Size(max = 1000)
    @Getter
    @Setter
    private String description;

    @Column(name = "logo")
    @Getter
    @Setter
    private byte[] logo;

    @Column(name = "deroulement")
    @Size(max = 1000)
    @Getter
    @Setter
    private String deroulement;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "etat_id")
    @Getter
    @Setter
    private Etat etat;

    @Column(name = "timezone")
    @Getter
    @Setter
    private String timeZone;

    @Column(name = "created_by")
    @CreatedBy
    @Getter
    @Setter
    private String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    @Getter
    @Setter
    private String updatedBy;

    @Column(name = "created_on")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date createdOn;

    @Column(name = "updated_on")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    private Date updatedOn;

    @Getter
    @Setter
    @Column(name = "date_evenement")
    private LocalDate dateEvenement;

    @Getter
    @Setter
    @Column(name = "heure_debut")
    private LocalTime heureDebut;

    @Getter
    @Setter
    @Column(name = "heure_fin")
    private LocalTime heureFin;

    @Column(name = "rome_id")
    @Getter
    @Setter
    private String romeId;

    @Column(name = "naf_code")
    @Getter
    @Setter
    private String nafCode;

    @Column(name = "est_a_publier")
    @Getter
    @Setter
    private Boolean estApublier;

    @Column(name = "lien_plus_information")
    @Getter
    @Setter
    private String lienPlusInformation;

    @Column(name = "libelle_lien_plus_information")
    @Getter
    @Setter
    private String libelleLienPlusInformation;

    @OneToMany(mappedBy = "evenement")
    @Getter
    @Setter
    private Set<PrerequisEvenement> prerequisList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "evenement")
    @Getter
    @Setter
    private List<CandidatEvenement> candidatEvenementList;

    @OneToMany(mappedBy = "evenement")
    @Getter
    @Setter
    private List<EvenementModaliteAcces> evenementModaliteAccesList;

    @NotAudited
    @ManyToMany
    @JoinTable(name = "evenement_offre", joinColumns = {@JoinColumn(name = "evenement_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "offre_id", nullable = false, updatable = false)})
    @Getter
    @Setter
    private Set<Offre> offres = new HashSet<>();

    @OneToMany(mappedBy = "evenement", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Getter
    @Setter
    private List<Intervenant> intervenants;

    @NotAudited
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "evenement_tag", joinColumns = @JoinColumn(name = "evenement_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Getter
    @Setter
    private Set<Tag> tags = new HashSet<>();

    @Getter
    @Setter
    @Transient
    private String statut;
    
    @Getter
    @Setter
    @Column(name = "date_annulation")
    private Date dateAnnulation;

    public Evenement(String titre, String description) {
    }

    @PrePersist
    public void prePersist() {
        createdOn = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = new Date();
    }

    public boolean encorePlaceDispo(ModaliteAcces modaliteAcces) throws ResourceNotFoundException {
        for (EvenementModaliteAcces acces : evenementModaliteAccesList) {
            if (acces.getModaliteAcces().getId().equals(modaliteAcces.getId())) {
                List<CandidatEvenement> candidats = this.candidatEvenementList.stream().filter(candidatEvenement ->
                        candidatEvenement.getModaliteAcces() !=null && candidatEvenement.getModaliteAcces().getId().equals(modaliteAcces.getId()) && candidatEvenement.isInscrit() && candidatEvenement.isPrerequisValider()
                ).collect(Collectors.toList());
                Integer count = Optional.ofNullable(candidats).isEmpty() ? 0 : candidats.size();
                return acces.getNombrePlace() > count;
            }
        }
        throw new ResourceNotFoundException("Rssource not fond for modaliteAcces" + modaliteAcces.getId());
    }

    public boolean complet(ModaliteAcces modaliteAcces) throws ResourceNotFoundException {
        for (EvenementModaliteAcces acces : evenementModaliteAccesList) {
            if (acces.getModaliteAcces().getId().equals(modaliteAcces.getId())) {
                List<CandidatEvenement> candidats = this.candidatEvenementList.stream().filter(candidatEvenement ->
                        candidatEvenement.getModaliteAcces() !=null && candidatEvenement.getModaliteAcces().getId().equals(modaliteAcces.getId()) && candidatEvenement.isInscrit() && candidatEvenement.isPrerequisValider()
                ).collect(Collectors.toList());
                Integer count = Optional.ofNullable(candidats).isEmpty() ? 0 : candidats.size();
                return acces.getNombrePlace().equals(count);
            }
        }
        throw new ResourceNotFoundException("Rssource not fond for modaliteAcces" + modaliteAcces.getId());
    }

    private Boolean isPasser() {
        LocalDateTime now = LocalDateTime.now();
        if (dateEvenement == null) {
            return null;
        }
        if (dateEvenement.isBefore(now.toLocalDate())) {
            return true;
        }

        if (dateEvenement.isAfter(now.toLocalDate())) {
            return false;
        }

        if (heureDebut.isBefore(now.toLocalTime().plusHours(1))) {
            return true;
        }

        if (heureFin == null) {
            return false;
        }
        LocalDateTime dateFinEvent = LocalDateTime.of(dateEvenement.getYear(), dateEvenement.getMonth(), dateEvenement.getDayOfMonth(), heureFin.getHour(), heureFin.getMinute());
        return dateFinEvent.isBefore(now);
    }

    public String getStatut() {
        if (this.etat.getId().equals(EStatut.BROUILLON.getId())) {
            this.statut = EStatut.BROUILLON.getLibelle();
        } else if (this.isPasser()) {
            this.statut = EStatut.TERMINER.getLibelle();
        } else if ((estApublier != null && estApublier) && this.isPasser() != null && !this.isPasser()) {
            this.statut = EStatut.PUBLIER.getLibelle();
        } else if (this.etat.getId().equals(EStatut.PRIVER.getId())) {
            this.statut = EStatut.PRIVER.getLibelle();
        }
        return this.statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return id.equals(evenement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getType() {
        Tag type = tags.stream().filter(tag -> tag.getTypeTag().getId().equals(2l)).findFirst().orElse(null);
        return type != null ? type.getLibelle() : null;
    }

    public Long getTypeId() {
        Tag type = tags.stream().filter(tag -> tag.getTypeTag().getId().equals(2l)).findFirst().orElse(null);
        return type != null ? type.getId() : null;
    }

    public String getPrerequis() {
        String prerequs = "";
        if (prerequisList != null && !prerequisList.isEmpty()) {
            for (PrerequisEvenement prerequisEvenement : prerequisList) {
                prerequs = prerequs.concat(prerequisEvenement.getPrerequis().getLibelle()).concat("|");
            }
            return prerequs;
        } else {
            return "";
        }
    }

    public String isSurPlace() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            EvenementModaliteAcces modaliteAcces = evenementModaliteAccesList.stream().filter(evenementModaliteAcces -> evenementModaliteAcces.getModaliteAcces().getId().equals(EModaliteAcces.PHYSIQUE.getId())).findFirst().orElse(null);

            return modaliteAcces != null ? "OUI" : "NON";
        }
        return "NON";
    }

    public int nbInscritSurPlace() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            List<CandidatEvenement> candidats = this.candidatEvenementList.stream().filter(candidatEvenement ->
                    candidatEvenement.getModaliteAcces() !=null && candidatEvenement.getModaliteAcces().getId().equals(EModaliteAcces.PHYSIQUE.getId()) && candidatEvenement.isInscrit()
            ).collect(Collectors.toList());
            return candidats != null ? candidats.size() : 0;
        }
        return 0;
    }

    public int nbPlaceSurPlace() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            EvenementModaliteAcces modaliteAcces = evenementModaliteAccesList.stream().filter(evenementModaliteAcces -> evenementModaliteAcces.getModaliteAcces().getId().equals(EModaliteAcces.PHYSIQUE.getId())).findFirst().orElse(null);

            return modaliteAcces != null && modaliteAcces.getNombrePlace()!=null ? modaliteAcces.getNombrePlace() : 0;
        }
        return 0;
    }

    public String isEnLigne() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            EvenementModaliteAcces modaliteAcces = evenementModaliteAccesList.stream().filter(evenementModaliteAcces -> evenementModaliteAcces.getModaliteAcces().getId().equals(EModaliteAcces.DISTANCE.getId())).findFirst().orElse(null);

            return modaliteAcces != null ? "OUI" : "NON";
        }
        return "NON";
    }

    public int nbInscrit(ModaliteAcces modaliteAcces) {
        List<CandidatEvenement> candidats = null;
      if(this.candidatEvenementList!=null && ! this.candidatEvenementList.isEmpty()) {
          candidats = this.candidatEvenementList.stream().filter(candidatEvenement ->
                  candidatEvenement.getModaliteAcces() !=null && candidatEvenement.getModaliteAcces().getId().equals(modaliteAcces.getId()) && candidatEvenement.isInscrit()
          ).collect(Collectors.toList());
      }
        return candidats != null ? candidats.size() : 0;

    }

    public Integer nbPresent(ModaliteAcces modaliteAcces) {

        List<CandidatEvenement> candidats = null;
        if(this.candidatEvenementList!=null && ! this.candidatEvenementList.isEmpty()) {
            candidats = this.candidatEvenementList.stream().filter(candidatEvenement ->
                    candidatEvenement.getModaliteAcces() !=null &&  candidatEvenement.getModaliteAcces().getId().equals(modaliteAcces.getId()) && candidatEvenement.isPresent()
            ).collect(Collectors.toList());
        }
        return candidats != null ? candidats.size() : 0;
    }

    public int nbInscritEnLigne() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            List<CandidatEvenement> candidats = this.candidatEvenementList.stream().filter(candidatEvenement ->
                    candidatEvenement.getModaliteAcces() !=null &&  candidatEvenement.getModaliteAcces().getId().equals(EModaliteAcces.DISTANCE.getId()) && candidatEvenement.isInscrit()
            ).collect(Collectors.toList());
            return candidats != null ? candidats.size() : 0;
        }
        return 0;
    }

    public String urlEnligne() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            EvenementModaliteAcces modaliteAcces = evenementModaliteAccesList.stream().filter(evenementModaliteAcces -> evenementModaliteAcces.getModaliteAcces().getId().equals(EModaliteAcces.DISTANCE.getId())).findFirst().orElse(null);

            return modaliteAcces != null && modaliteAcces.getUrlAcces() != null ? modaliteAcces.getUrlAcces() : "";
        }
        return "";
    }

    public int nbPlaceEnLigne() {
        if (evenementModaliteAccesList != null && !evenementModaliteAccesList.isEmpty()) {
            EvenementModaliteAcces modaliteAcces = evenementModaliteAccesList.stream().filter(evenementModaliteAcces -> evenementModaliteAcces.getModaliteAcces().getId().equals(EModaliteAcces.DISTANCE.getId())).findFirst().orElse(null);

            return modaliteAcces != null && modaliteAcces.getNombrePlace() != null ? modaliteAcces.getNombrePlace() : 0;
        }
        return 0;
    }

    public String getObjectifs() {
        if (tags != null && !tags.isEmpty()) {
            List<Tag> tagList = tags.stream()
                    .filter(tag -> tag.getTypeTag().getId().equals(ECategorieTag.OBJECTIF.getId()))
                    .collect(Collectors.toList());
            return tagList != null ? tagList.toString() : "";
        }
        return "";
    }

    public String getOperations() {
        if (tags != null && !tags.isEmpty()) {
            List<Tag> tagList = tags.stream()
                    .filter(tag -> tag.getTypeTag().getId().equals(ECategorieTag.OPERATION.getId()))
                    .collect(Collectors.toList());
            return tagList != null ? tagList.toString() : "";
        }
        return "";
    }

    public String getBenefices() {
        if (tags != null && !tags.isEmpty()) {
            List<Tag> tagList = tags.stream()
                    .filter(tag -> tag.getTypeTag().getId().equals(ECategorieTag.BENEFICE.getId()))
                    .collect(Collectors.toList());
            return tagList != null ? tagList.toString() : "";
        }
        return "";
    }

    public String getPublicCibles() {
        if (tags != null && !tags.isEmpty()) {
            List<Tag> tagList = tags.stream()
                    .filter(tag -> tag.getTypeTag().getId().equals(ECategorieTag.CARACTERISTIQUE_CIBLE.getId()))
                    .collect(Collectors.toList());
            return tagList != null ? tagList.toString() : "";
        }
        return "";
    }
}
