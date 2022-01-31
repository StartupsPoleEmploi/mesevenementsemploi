package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatEvenementRepository extends PagingAndSortingRepository<CandidatEvenement, CandidatEvenementKey> {
    List<CandidatEvenement> findAllByEvenement(Evenement evenement);

    List<CandidatEvenement> findAllByEvenementAndStatutInscription(Evenement evenement, StatutInscription statut);

    List<CandidatEvenement> findAllByEvenementAndAndModaliteAccesAndStatutInscription(Evenement evenement, ModaliteAcces modaliteAcces, StatutInscription statut);

    long countAllByEvenementAndModaliteAccesAndStatutInscription(Evenement evenement, ModaliteAcces modaliteAcces, StatutInscription statut);

    long countAllByEvenementAndModaliteAccesAndPresentTrue(Evenement evenement, ModaliteAcces modaliteAcces);

    void deleteAllByEvenement(Evenement evenement);

    @Modifying
    @Query("UPDATE CandidatEvenement c set c.statutInscription.id = :statutId , c.dateModification = CURRENT_TIMESTAMP where c.id.candidatId = :candidatId and c.id.evenementId = :evenementId")
    int updateStatut(@Param("candidatId") Long candidatId, @Param("evenementId") Long evenementId, @Param("statutId") Integer statutId);
}
