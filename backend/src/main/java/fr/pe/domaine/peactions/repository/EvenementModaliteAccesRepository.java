package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.EvenementModaliteAcces;
import fr.pe.domaine.peactions.model.EvenementModaliteAccesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvenementModaliteAccesRepository extends JpaRepository<EvenementModaliteAcces, EvenementModaliteAccesKey> {
    List<EvenementModaliteAcces> findAllByEvenement(Evenement evenement);

    void deleteAllByEvenement(Evenement evenement);
}
