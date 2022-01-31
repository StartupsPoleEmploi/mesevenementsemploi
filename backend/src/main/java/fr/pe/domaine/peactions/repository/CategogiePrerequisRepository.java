package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.CategoriePrerequis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategogiePrerequisRepository extends JpaRepository<CategoriePrerequis, Long> {

}
