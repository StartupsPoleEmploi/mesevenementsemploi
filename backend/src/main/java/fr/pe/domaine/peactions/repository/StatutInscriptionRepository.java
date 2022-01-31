package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.StatutInscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutInscriptionRepository extends JpaRepository<StatutInscription, Integer> {

}
