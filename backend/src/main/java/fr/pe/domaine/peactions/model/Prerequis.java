package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;


@Entity
@Table(name = "prerequis")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@NoArgsConstructor
public class Prerequis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prerequis_generator")
    @SequenceGenerator(name = "prerequis_generator", sequenceName = "prerequis_seq", allocationSize = 1)
    @Column(name = "prerequis_id")
    @Getter
    @Setter
    private Long id;
    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "categorie_prerequis_id")
    @Getter
    @Setter
    private CategoriePrerequis categorie;

}
