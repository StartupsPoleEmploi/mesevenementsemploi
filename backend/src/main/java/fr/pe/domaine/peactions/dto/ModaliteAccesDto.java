package fr.pe.domaine.peactions.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ModaliteAccesDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String libelle;


    public ModaliteAccesDto(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
}
