package fr.pe.domaine.peactions.emploistoredev.ressources;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class DateNaissanceESD {

    @Getter
    @Setter
    private String codeCivilite;

    @Getter
    @Setter
    private String libelleCivilite;

    @Getter
    @Setter
    private String nomPatronymique;

    @Getter
    @Setter
    private String nomMarital;

    @Getter
    @Setter
    private String prenom;

    @Getter
    @Setter
    private Date dateDeNaissance;

    @Override
    public String toString() {
        return "DateNaissanceESD [codeCivilite=" + codeCivilite + ", libelleCivilite=" + libelleCivilite
                + ", nomPatronymique=" + nomPatronymique + ", nomMarital=" + nomMarital + ", prenom=" + prenom
                + ", dateDeNaissance=" + dateDeNaissance + "]";
    }
}
