package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Etablissement;
import fr.pe.domaine.peactions.model.Etat;
import fr.pe.domaine.peactions.model.Evenement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EvenementRepository extends PagingAndSortingRepository<Evenement, Long> {
    Page<Evenement> findAllByEtablissement(Etablissement etablissement, Pageable pageable);

    List<Evenement> findAllByDateEvenementAndEstApublierTrueAndEtatOrderByDateEvenementAsc(LocalDate date, Etat etat);

    List<Evenement> findAllByDateEvenementAndEstApublierTrueAndEtatAndHeureDebutAfterAndHeureDebutBeforeOrderByDateEvenementAsc(LocalDate date, Etat etat, LocalTime heureDebut, LocalTime heureFin);

    @Query("SELECT e FROM Evenement e WHERE e.estApublier=true AND e.etat.id = 1 AND (e.dateEvenement > CURRENT_DATE OR (e.dateEvenement = CURRENT_DATE  AND e.heureDebut > CURRENT_TIME)) order by e.dateEvenement ASC, e.heureDebut ASC, e.titre ASC")
    Page<Evenement> findAllByEstApublierTrue(Pageable paging);

    @Query("SELECT e FROM Evenement e WHERE e.estApublier=true AND e.etat.id = 1 AND (e.dateEvenement > CURRENT_DATE OR (e.dateEvenement = CURRENT_DATE  AND e.heureDebut > CURRENT_TIME)) AND  e.codePostal  like concat(:codeDpt,'%') order by e.dateEvenement ASC, e.heureDebut ASC, e.titre ASC")
    Page<Evenement> findAllByDeptAndEstApublierTrue(@Param("codeDpt") String codeDpt, Pageable paging);

    @Query("SELECT DISTINCT e FROM Evenement e LEFT JOIN e.evenementModaliteAccesList em WHERE e.estApublier=true AND e.etat.id = 1 AND e.dateEvenement >= CURRENT_DATE AND  em.modaliteAcces.id = 0   order by e.dateEvenement")
    Page<Evenement> findAllAdistance(Pageable paging);

    @Query("SELECT count(DISTINCT e) FROM Evenement e LEFT JOIN e.evenementModaliteAccesList em WHERE e.estApublier=true AND e.etat.id = 1 AND e.dateEvenement >= CURRENT_DATE AND  em.modaliteAcces.id = 0")
    Long countAllADistance();

    @Query("SELECT count(e) FROM Evenement e WHERE  e.estApublier=true AND e.etat.id = 1 AND e.dateEvenement >= CURRENT_DATE AND  e.codePostal like concat(:codeDpt,'%') ")
    Long countByDeptAndEstApublierTrue(@Param("codeDpt") String codeDpt);

    List<Evenement> getAllByEtablissement(Etablissement etablissement);

    @Query("SELECT e FROM Evenement e WHERE (e.codePostal like concat(:codeDpt,'%'))")
    List<Evenement> findByCodeEtablissementStartingWith(@Param("codeDpt") String codeDpt);

    List<Evenement> findAll();

    Long countByEtablissement(Etablissement etablissement);

    Long countByEtat(Etat brouillon);

    @Query("SELECT count(e)  FROM Evenement e WHERE e.estApublier IS NOT null AND e.estApublier=true AND e.etat.id = 1 AND (e.dateEvenement > CURRENT_DATE  OR ( e.dateEvenement = CURRENT_DATE AND e.heureDebut > CURRENT_TIME))")
    Long countByEstApublierTrue();

    @Query("SELECT count(e)  FROM Evenement e WHERE e.estApublier=true AND e.etat.id = 1 AND (e.dateEvenement < CURRENT_DATE OR (e.dateEvenement = current_date AND e.heureDebut <= current_time ))")
    Long countByEstTermineDuJour();

    @Query("SELECT e FROM Evenement e WHERE e.dateEvenement > CURRENT_DATE OR ( e.dateEvenement = CURRENT_DATE AND e.heureDebut > CURRENT_TIME) order by e.dateEvenement ASC, e.heureDebut ASC, e.titre ASC")
    List<Evenement> getAllByDateEvenementAfterNow();

    @Query("SELECT e FROM Evenement e WHERE e.dateEvenement < CURRENT_DATE OR ( e.dateEvenement = CURRENT_DATE AND e.heureDebut < CURRENT_TIME ) order by e.dateEvenement DESC, e.heureDebut DESC, e.titre ASC")
    List<Evenement> getEvenementByDateEvenementBeforeNow();

    @Query("SELECT e FROM Evenement e WHERE e.etablissement.codeEtablissement = :codeEtablissement AND (e.dateEvenement > CURRENT_DATE OR ( e.dateEvenement = CURRENT_DATE AND e.heureDebut > CURRENT_TIME)) order by e.dateEvenement ASC, e.heureDebut ASC, e.titre ASC")
    List<Evenement> getByCodeEtablissementAllAndDateEvenementAfterNow(@Param("codeEtablissement") String codeEtablissement);

    @Query("SELECT e FROM Evenement e WHERE e.etablissement.codeEtablissement = :codeEtablissement AND (e.dateEvenement < CURRENT_DATE OR ( e.dateEvenement = CURRENT_DATE AND e.heureDebut < CURRENT_TIME )) order by e.dateEvenement DESC, e.heureDebut DESC, e.titre ASC ")
    List<Evenement> getByCodeEtablissementEvenementByDateEvenementBeforeNow(@Param("codeEtablissement") String codeEtablissement);
}
