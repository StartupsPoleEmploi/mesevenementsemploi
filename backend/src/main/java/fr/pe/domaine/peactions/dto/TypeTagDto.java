package fr.pe.domaine.peactions.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class TypeTagDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty(notes = "libelle du type de tag ", dataType = "string", example = "Bénéfice de participation")
    private String libelle;
}
