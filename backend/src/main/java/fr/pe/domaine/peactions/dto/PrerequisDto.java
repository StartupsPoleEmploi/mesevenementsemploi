package fr.pe.domaine.peactions.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class PrerequisDto {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    @ApiModelProperty(notes = "Catégorie du prérequis", dataType = "long", example = "1")
    private Long categorieId;
    @Getter
    @Setter
    @ApiModelProperty(notes = "libelle du prérequis ", dataType = "string", example = "Avoir un Bac+5 ou plus")
    private String libelle;


    public PrerequisDto(Long id, Long categorieId, String libelle) {
        this.id = id;
        this.categorieId = categorieId;
        this.libelle = libelle;
    }

    public PrerequisDto() {
    }
}
