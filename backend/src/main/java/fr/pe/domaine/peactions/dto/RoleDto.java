package fr.pe.domaine.peactions.dto;

import fr.pe.domaine.peactions.commun.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;


public class RoleDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private ERole name;
}
