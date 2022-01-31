package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

    @Modifying
    @Query(value = "DELETE FROM evenement_offre WHERE evenement_id = ?1", nativeQuery = true)
    void deleteAllByEvenement(Long evenementId);

}
