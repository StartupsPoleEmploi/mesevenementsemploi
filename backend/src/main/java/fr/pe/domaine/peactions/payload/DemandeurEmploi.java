package fr.pe.domaine.peactions.payload;

import lombok.Getter;
import lombok.Setter;

public class DemandeurEmploi {

    @Getter
    @Setter
    private String idPoleEmploi;

    @Getter
    @Setter
    private InformationsPersonnelles informationsPersonnelles;


}
