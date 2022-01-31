package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public @Embeddable
class EvenementModaliteAccesKey implements Serializable {

    @Getter
    @Setter
    private Long evenementId;

    @Getter
    @Setter
    private Long modaliteAccesId;

    public EvenementModaliteAccesKey(ModaliteAcces modaliteAcces, Evenement evenement) {
        this.modaliteAccesId = modaliteAcces.getId();
        this.evenementId = evenement.getId();
    }

    public EvenementModaliteAccesKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvenementModaliteAccesKey key = (EvenementModaliteAccesKey) o;
        return modaliteAccesId.equals(key.modaliteAccesId) &&
                evenementId.equals(key.evenementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modaliteAccesId, evenementId);
    }
}
