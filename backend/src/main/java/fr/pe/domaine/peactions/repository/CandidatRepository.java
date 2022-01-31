package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Candidat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidatRepository extends PagingAndSortingRepository<Candidat, Long> {
    Candidat findByIdentifiantCrypter(String identifiant);
}
