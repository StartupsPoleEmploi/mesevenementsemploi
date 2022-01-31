package fr.pe.domaine.peactions.emploistoredev.ressources;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class RomeESD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String libelle;
}
