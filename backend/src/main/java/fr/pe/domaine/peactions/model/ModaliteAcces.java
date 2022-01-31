package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Entity
@Table(name = "modalite_acces")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@NoArgsConstructor
public class ModaliteAcces {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "modalite_acces_generator")
    @SequenceGenerator(name = "modalite_acces_generator", sequenceName = "modalite_acces_seq", allocationSize = 1)
    @Column(name = "modalite_acces_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;


}
