package fr.pe.domaine.peactions.dto;


import lombok.Getter;
import lombok.Setter;

public class IntervenantDto {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String libelle;
    @Getter
    @Setter
    private String contact;
    @Getter
    @Setter
    private String siteWeb;
}
