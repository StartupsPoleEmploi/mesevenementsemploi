package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "sondage_post_evenement")

@NoArgsConstructor
public class SondagePostEvenement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sondage_generator")
    @SequenceGenerator(name = "sondage_generator", sequenceName = "sondage_seq", allocationSize = 1)
    @Column(name = "sondage_id")
    @Getter
    @Setter
    private long id;


    @Getter
    @Setter
    @OneToOne(mappedBy = "sondagePostEvenement")
    private CandidatEvenement candidatEvenement;


}
