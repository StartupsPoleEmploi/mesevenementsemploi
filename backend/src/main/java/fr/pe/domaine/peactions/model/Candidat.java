package fr.pe.domaine.peactions.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Audited
@Entity
@Data
@NoArgsConstructor
@Table(name = "candidat")
@ToString(of = {"identifiantCrypter", "nom", "prenom"})
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidat_generator")
    @SequenceGenerator(name = "candidat_generator", sequenceName = "candidat_seq", allocationSize = 1)
    @Column(name = "candidat_id")
    @Getter
    @Setter
    private Long id;

    @Column(name = "id_rci")
    @Getter
    @Setter
    private Long identifiantRci;

    @Column(name = "identifiant_crypter")
    @Getter
    @Setter
    private String identifiantCrypter;

    @Column(name = "civilite")
    @Getter
    @Setter
    private String civilite;

    @Column(name = "nom")
    @Getter
    @Setter
    private String nom;

    @Column(name = "prenom")
    @Getter
    @Setter
    private String prenom;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @Column(name = "date_de_naissance")
    private Date dateNaissance;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidat")
    @Getter
    @Setter
    private Set<CandidatEvenement> candidatEvenements;
}
