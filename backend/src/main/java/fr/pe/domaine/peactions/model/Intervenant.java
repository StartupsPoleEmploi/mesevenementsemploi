package fr.pe.domaine.peactions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "intervenant")
@Audited
@NoArgsConstructor
public class Intervenant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "intervenant_generator")
    @SequenceGenerator(name = "intervenant_generator", sequenceName = "intervenant_seq", allocationSize = 1)
    @Column(name = "intervenant_id")
    @Getter
    @Setter
    private long id;

    @Column(name = "libelle")
    @Getter
    @Setter
    private String libelle;

    @Column(name = "contact")
    @Getter
    @Setter
    private String contact;

    @Column(name = "site_web")
    @Getter
    @Setter
    private String siteWeb;

    @ManyToOne
    @JoinColumn(name = "evenement_id")
    @Getter
    @Setter
    private Evenement evenement;

    @Override
    public String toString() {
        return "{" +
                "libelle='" + libelle + '\'' +
                ", siteWeb='" + siteWeb + '\'' +
                '}';
    }
}
