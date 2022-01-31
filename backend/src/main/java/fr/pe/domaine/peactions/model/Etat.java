package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "etat")
@NoArgsConstructor

public class Etat {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "etat_generator")
    @SequenceGenerator(name = "etat_generator", sequenceName = "etat_seq", allocationSize = 1)
    @Column(name = "etat_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;


}
