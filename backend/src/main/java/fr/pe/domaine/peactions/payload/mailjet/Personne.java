package fr.pe.domaine.peactions.payload.mailjet;

import lombok.Getter;
import lombok.Setter;

public class Personne {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String messageUUID;

    @Getter
    @Setter
    private String messageID;

    @Getter
    @Setter
    private String messageHref;

    public Personne() {

    }

    public Personne(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
