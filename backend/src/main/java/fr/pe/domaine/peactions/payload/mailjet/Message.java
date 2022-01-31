package fr.pe.domaine.peactions.payload.mailjet;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class Message {

    @Getter
    @Setter
    private Personne from;

    @Getter
    @Setter
    private List<Personne> to;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private long templateID;

    @Getter
    @Setter
    private boolean templateLanguage = true;

    @Getter
    @Setter
    private Map<String, String> variables;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String customID;


}
