package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "statut_inscription")
public class StatutInscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "statut_id")
    private Integer id;

    @Column(name = "libelle", length = 20)
    @Getter
    @Setter
    private String libelle;

    public StatutInscription() {
    }

}
