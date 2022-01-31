package fr.pe.domaine.peactions.emploistoredev.ressources;

import lombok.Getter;
import lombok.Setter;

public class CoordonneesESD {

    @Getter
    @Setter
    private String adresse1;
    @Getter
    @Setter
    private String adresse2;
    @Getter
    @Setter
    private String adresse3;
    @Getter
    @Setter
    private String adresse4;
    @Getter
    @Setter
    private String codePostal;
    @Getter
    @Setter
    private String codeINSEE;
    @Getter
    @Setter
    private String libelleCommune;
    @Getter
    @Setter
    private String codePays;
    @Getter
    @Setter
    private String libellePays;

    @Override
    public String toString() {
        return "CoordonneesESD [adresse1=" + adresse1 + ", adresse2=" + adresse2 + ", adresse3=" + adresse3
                + ", adresse4=" + adresse4 + ", codePostal=" + codePostal + ", codeINSEE=" + codeINSEE
                + ", libelleCommune=" + libelleCommune + ", codePays=" + codePays + ", libellePays=" + libellePays
                + "]";
    }
}
