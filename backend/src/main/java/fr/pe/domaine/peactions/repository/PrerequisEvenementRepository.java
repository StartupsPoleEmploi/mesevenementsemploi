package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.Prerequis;
import fr.pe.domaine.peactions.model.PrerequisEvenement;
import fr.pe.domaine.peactions.model.PrerequisEvenementKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrerequisEvenementRepository extends JpaRepository<PrerequisEvenement, PrerequisEvenementKey> {

    List<PrerequisEvenement> findAllByEvenement(Evenement evenement);

    PrerequisEvenement findPrerequisEvenementByEvenementAndPrerequis(Evenement evenement, Prerequis prerequis);

    void deleteAllByEvenement(Evenement evenement);
}
