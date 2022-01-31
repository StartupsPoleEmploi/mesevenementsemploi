package fr.pe.domaine.peactions.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class OffreDto {

    @Getter
    @Setter
    @ApiModelProperty(notes = "identifiant de l'offre", dataType = "string", example = "114CJPN")
    private String id;

    public OffreDto(String identifiant) {
        this.id = identifiant;
    }

    public OffreDto() {
    }
}
