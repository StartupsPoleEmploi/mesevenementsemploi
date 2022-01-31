package fr.pe.domaine.peactions.dto;

import lombok.Getter;
import lombok.Setter;

public class ModeAccees {
    @Getter
    @Setter
    private ModaliteAccesDto modaliteAcces;
    @Getter
    @Setter
    private Integer nombrePlace;
    @Getter
    @Setter
    private Integer nombreInscrit;
    @Getter
    @Setter
    private Integer nombrePresent;
    @Getter
    @Setter
    private String urlParticipation;

    public ModeAccees(Long id, String libelle, Integer nombreInscrit, Integer nombrePlace, Integer nombrePresent, String urlParticipation) {
        this.modaliteAcces = new ModaliteAccesDto(id, libelle);
        this.nombreInscrit = nombreInscrit;
        this.nombrePresent = nombrePresent;
        this.nombrePlace = nombrePlace;
        this.urlParticipation = urlParticipation;
    }

    public ModeAccees() {

    }
}
