package fr.pe.domaine.peactions.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SondagePostEvenementDto {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private CandidatDto candidatDto;


}
