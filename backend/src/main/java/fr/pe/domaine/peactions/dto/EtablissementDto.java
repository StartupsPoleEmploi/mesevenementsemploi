package fr.pe.domaine.peactions.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class EtablissementDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String libelle;

    @Getter
    @Setter
    private String codeEtablissement;
}
