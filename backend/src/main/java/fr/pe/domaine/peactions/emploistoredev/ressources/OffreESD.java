package fr.pe.domaine.peactions.emploistoredev.ressources;

import lombok.Getter;
import lombok.Setter;

public class OffreESD {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String intitule;

    @Getter
    @Setter
    private String typeContrat;

    @Getter
    @Setter
    private String dureeTravailLibelle;

    @Getter
    @Setter
    private String dureeTravailLibelleConverti;

    @Getter
    @Setter
    private OrigineOffre origineOffre;
}
