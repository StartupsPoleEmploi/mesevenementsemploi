package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Naf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NafRepository extends JpaRepository<Naf, String> {
}
