package fr.pe.domaine.peactions.payload.request;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
    @Getter
    @Setter
    private String identifiant;

    @Getter
    @Setter
    private String motDePasse;


}
