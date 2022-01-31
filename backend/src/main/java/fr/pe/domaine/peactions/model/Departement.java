package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "departement")
public class Departement {

    @Getter
    @Setter
    @Column(name = "departement_id")
    public Integer id;

    @Getter
    @Setter
    @Id
    @Column(name = "departement_code")
    public String code;

    @Getter
    @Setter
    @Column(name = "departement_nom")
    public String libelle;
}
