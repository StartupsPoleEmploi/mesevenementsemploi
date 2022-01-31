package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public
class CandidatEvenementKey implements Serializable {

    @Getter
    @Setter
    private Long candidatId;

    @Getter
    @Setter
    private Long evenementId;

    public CandidatEvenementKey(Candidat candidat, Evenement evenement) {
        this.candidatId = candidat.getId();
        this.evenementId = evenement.getId();
    }

    public CandidatEvenementKey() {

    }

    public CandidatEvenementKey(long candidatId, long evenementId) {
        this.candidatId = candidatId;
        this.evenementId = evenementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidatEvenementKey key = (CandidatEvenementKey) o;
        return candidatId.equals(key.candidatId) &&
                evenementId.equals(key.evenementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidatId, evenementId);
    }
}
