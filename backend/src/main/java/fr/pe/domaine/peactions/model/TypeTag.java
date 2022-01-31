package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
@Entity
@Table(name = "type_tag")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@NoArgsConstructor
public class TypeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_tag_generator")
    @SequenceGenerator(name = "type_tag_generator", sequenceName = "type_tag_seq", allocationSize = 1)
    @Column(name = "type_tag_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;
}
