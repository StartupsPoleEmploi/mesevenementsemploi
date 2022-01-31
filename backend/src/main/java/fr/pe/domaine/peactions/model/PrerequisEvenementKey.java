package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PrerequisEvenementKey implements Serializable {

    @Getter
    @Setter
    private Long prerequisId;

    @Getter
    @Setter
    private Long evenementId;

    public PrerequisEvenementKey(Prerequis prerequis, Evenement evenement) {
        this.evenementId = evenement.getId();
        this.prerequisId = prerequis.getId();
    }

    public PrerequisEvenementKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrerequisEvenementKey key = (PrerequisEvenementKey) o;
        return prerequisId.equals(key.prerequisId) &&
                evenementId.equals(key.evenementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prerequisId, evenementId);
    }
}
