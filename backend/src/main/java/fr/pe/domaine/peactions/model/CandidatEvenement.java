package fr.pe.domaine.peactions.model;

import fr.pe.domaine.peactions.utils.Constantes;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Audited
@Entity
@Table(name = "candidat_evenement")
public class CandidatEvenement {

    @EmbeddedId
    @Getter
    @Setter
    private CandidatEvenementKey id;

    @ManyToOne
    @MapsId("candidatId")
    @JoinColumn(name = "candidat_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter
    @Setter
    private Candidat candidat;

    @ManyToOne
    @MapsId("evenementId")
    @JoinColumn(name = "evenement_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter
    @Setter
    private Evenement evenement;

    @Getter
    @Setter
    private boolean present;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "modalite_acces_id", nullable = true)
    private ModaliteAcces modaliteAcces;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "sondage_id", nullable = true)
    private SondagePostEvenement sondagePostEvenement;
    
    @Getter
    @Setter
    @Column(name = "date_inscription")
    private Date dateInscription;

    @Getter
    @Setter
    @Column(name = "date_modification")
    private Date dateModification;

    @Getter
    @Setter
    @Column(name = "date_desinscription")
    private Date dateDesinscription;

    @ManyToOne
    @JoinColumn(name = "statut_inscription", nullable = false)
    @Getter
    @Setter
    private StatutInscription statutInscription;

    @PrePersist
    public void prePersist() {
        dateInscription = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        dateModification = new Date();
    }


    public CandidatEvenement(Candidat candidat, Evenement evenement) {

        this.id = new CandidatEvenementKey(candidat, evenement);
        this.candidat = candidat;
        this.evenement = evenement;
    }

    public CandidatEvenement() {

    }

    public CandidatEvenement(Candidat candidat, Evenement evenement, boolean present) {
        this(candidat, evenement);
        this.present = present;
    }


    public CandidatEvenement(Candidat candidat, Evenement evenement, boolean present, ModaliteAcces modaliteAcces) {
        this(candidat, evenement);
        this.present = present;
        this.modaliteAcces = modaliteAcces;
    }

    public boolean isInscrit() {
        return this.statutInscription != null && this.statutInscription.getId().equals(Constantes.STATUT_INSCRIT);
    }

    public boolean isPrerequisValider() {
        return this.statutInscription != null && this.statutInscription.getId().equals(1);
    }

    public Boolean isAnnulation() {
        return this.statutInscription != null && this.statutInscription.getId().equals(Constantes.STATUT_ANNULATION);
    }

}
