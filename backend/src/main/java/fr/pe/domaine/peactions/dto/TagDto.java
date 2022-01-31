package fr.pe.domaine.peactions.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class TagDto {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    @ApiModelProperty(notes = "libelle du tag ", dataType = "string", example = "Ouvert aux jeunes")
    private String libelle;
    @Getter
    @Setter
    @ApiModelProperty(notes = "identifiant du type de tag ", dataType = "long", example = "2")
    private Long typeTagId;


    public TagDto(Long id, String libelle, Long typeTagId) {
        this.id = id;
        this.libelle = libelle;
        this.typeTagId = typeTagId;
    }

    public TagDto() {
    }
}
