package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
@Table(name = "prerequis_evenement")
public class PrerequisEvenement {

    @EmbeddedId
    @Getter
    @Setter
    PrerequisEvenementKey id;

    @ManyToOne
    @MapsId("prerequisId")
    @JoinColumn(name = "prerequis_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter
    @Setter
    Prerequis prerequis;

    @ManyToOne
    @MapsId("evenementId")
    @JoinColumn(name = "evenement_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter
    @Setter
    Evenement evenement;

    @Getter
    @Setter
    private boolean indispensable;

    public PrerequisEvenement(Prerequis prerequis, Evenement evenement) {

        this.id = new PrerequisEvenementKey(prerequis, evenement);
        this.prerequis = prerequis;
        this.evenement = evenement;

    }

    public PrerequisEvenement() {

    }

}
