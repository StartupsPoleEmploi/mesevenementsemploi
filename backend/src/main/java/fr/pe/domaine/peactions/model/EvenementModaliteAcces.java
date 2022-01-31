package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

@Audited
@Entity
@Table(name = "evenement_modalite_acces")
public class EvenementModaliteAcces {

    @EmbeddedId
    @Getter
    @Setter
    private EvenementModaliteAccesKey id;

    @ManyToOne
    @MapsId("modaliteAccesId")
    @JoinColumn(name = "modalite_acces_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter
    @Setter
    private ModaliteAcces modaliteAcces;


    @ManyToOne
    @MapsId("evenementId")
    @JoinColumn(name = "evenement_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Getter
    @Setter
    private Evenement evenement;

    @Column(name = "nombre_place")
    @Getter
    @Setter
    private Integer nombrePlace;


    @Column(name = "url_acces")
    @Getter
    @Setter
    private String urlAcces;

    public EvenementModaliteAcces(ModaliteAcces modaliteAcces, Evenement evenement, Integer nombrePlace, String urlAcces) {
        this(modaliteAcces, evenement);
        this.nombrePlace = nombrePlace;
        this.urlAcces = urlAcces;
    }

    public EvenementModaliteAcces() {

    }

    public EvenementModaliteAcces(ModaliteAcces modaliteAcces, Evenement evenement) {
        this.id = new EvenementModaliteAccesKey(modaliteAcces, evenement);
        this.modaliteAcces = modaliteAcces;
        this.evenement = evenement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvenementModaliteAcces that = (EvenementModaliteAcces) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


