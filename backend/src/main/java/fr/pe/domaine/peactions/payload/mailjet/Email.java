package fr.pe.domaine.peactions.payload.mailjet;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Email {

    @Getter
    @Setter
    private List<Message> messages;

    public Email(List<Message> messages) {
        this.messages = messages;
    }
}
