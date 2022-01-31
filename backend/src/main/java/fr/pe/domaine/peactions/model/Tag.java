package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
    @SequenceGenerator(name = "tag_generator", sequenceName = "tag_seq", allocationSize = 1)
    @Column(name = "tag_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;

    @Column(name = "est_a_publier")
    @Getter
    @Setter
    private boolean estApublier;

    @ManyToOne
    @JoinColumn(name = "type_tag")
    @Getter
    @Setter
    private TypeTag typeTag;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tags")
    @Getter
    @Setter
    private Set<Evenement> evenements;


    @Override
    public String toString() {
        return libelle;
    }
}
