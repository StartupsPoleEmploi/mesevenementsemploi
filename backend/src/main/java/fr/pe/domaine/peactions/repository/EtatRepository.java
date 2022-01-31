package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatRepository extends JpaRepository<Etat, Long> {

}
