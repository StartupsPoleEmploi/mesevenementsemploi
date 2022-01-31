package fr.pe.domaine.peactions.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class InformationsPersonnelles {

    @Getter
    @Setter
    private String codePostal;

    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String nom;

    @Getter
    @Setter
    private String prenom;


    @Override
    public String toString() {
        return "InformationsPersonnelles{" +
                "codePostal='" + codePostal + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
