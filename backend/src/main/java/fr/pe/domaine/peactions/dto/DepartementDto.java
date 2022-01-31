package fr.pe.domaine.peactions.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

public class DepartementDto {

    @Getter
    @Setter
    public Integer id;

    @Getter
    @Setter
    public String code;

    @Getter
    @Setter
    public String libelle;
}
