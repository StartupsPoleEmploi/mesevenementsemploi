package fr.pe.domaine.peactions.commun.enumeration;

import lombok.Getter;
import lombok.Setter;

public enum ECategorieTag {
    CARACTERISTIQUE_CIBLE(0l),
    BENEFICE(1l),
    TYPE(2l),
    OBJECTIF(3l),
    OPERATION(4l);


    @Getter
    @Setter
    private Long id;

    ECategorieTag(Long id) {
        this.id = id;
    }
}

