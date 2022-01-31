package fr.pe.domaine.peactions.commun.enumeration;

import lombok.Getter;
import lombok.Setter;

public enum EStatut {
    PUBLIER("Publié"),
    BROUILLON(0l, "Brouillon"),
    TERMINER("Terminé"),
    PRIVER(1l, "Privé");

    @Getter
    @Setter
    private final String libelle;

    @Getter
    @Setter
    private Long id;

    EStatut(String libelle) {
        this.libelle = libelle;
    }

    EStatut(Long id, String libelle) {
        this(libelle);
        this.id = id;
    }
}

