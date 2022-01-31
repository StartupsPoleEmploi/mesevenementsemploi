package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "categorie_prerequis")

@NoArgsConstructor
public class CategoriePrerequis {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorie_prerequis_generator")
    @SequenceGenerator(name = "categorie_prerequis_generator", sequenceName = "categorie_prerequis_seq", allocationSize = 1)
    @Column(name = "categorie_prerequis_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;
}
