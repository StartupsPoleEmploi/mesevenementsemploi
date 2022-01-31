package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "offre")
@NoArgsConstructor
public class Offre {

    @Id
    @Column(name = "offre_id")
    @Getter
    @Setter
    private String id;

    @ManyToMany(mappedBy = "offres", fetch = FetchType.LAZY)
    private final Set<Evenement> evenements = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offre offre = (Offre) o;
        return id.equals(offre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
