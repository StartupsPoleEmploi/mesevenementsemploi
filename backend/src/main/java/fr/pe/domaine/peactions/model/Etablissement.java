package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "etablissement")
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etablissement_id")
    @Getter
    @Setter
    private Long id;


    @Size(max = 20)
    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;


    @Size(max = 20)
    @Column(name = "code_etablissement", unique = true)
    @Getter
    @Setter
    private String codeEtablissement;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etablissement_parent_code", referencedColumnName = "code_etablissement")
    private Etablissement parent;

    @Size(max = 50)
    @Getter
    @Setter
    private String email;

    @Size(max = 120)
    @Getter
    @Setter
    @Column(name = "mot_de_passe")
    private String motDePasse;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "etablissement_roles",
            joinColumns = @JoinColumn(name = "etablissement_code", referencedColumnName = "code_etablissement"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    @Getter
    @Setter
    private Set<Role> roles = new HashSet<>();

    public Etablissement() {
    }

    public Etablissement(String codeEtablissement, String motDePasse) {
        this.codeEtablissement = codeEtablissement;
        this.motDePasse = motDePasse;
    }
}
