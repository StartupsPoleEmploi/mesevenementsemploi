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
@Table(name = "naf")
public class Naf {

    @Getter
    @Setter
    @Column
    public String type;

    @Getter
    @Setter
    @Id
    public String code;

    @Getter
    @Setter
    public String libelle;

    @Getter
    @Setter
    public String niveau;
}
