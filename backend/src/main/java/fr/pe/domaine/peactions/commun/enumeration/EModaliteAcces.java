package fr.pe.domaine.peactions.commun.enumeration;

import lombok.Getter;
import lombok.Setter;

public enum EModaliteAcces {
    DISTANCE(0l, "Distance"),
    PHYSIQUE(1l, "Physique");

    @Getter
    @Setter
    private final String libelle;

    @Getter
    @Setter
    private Long id;

    EModaliteAcces(String libelle) {
        this.libelle = libelle;
    }

    EModaliteAcces(Long id, String libelle) {
        this(libelle);
        this.id = id;
    }
}

