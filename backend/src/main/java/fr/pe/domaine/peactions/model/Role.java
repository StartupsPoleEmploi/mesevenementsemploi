package fr.pe.domaine.peactions.model;

import fr.pe.domaine.peactions.commun.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "libelle", length = 20)
    @Getter
    @Setter
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

}
