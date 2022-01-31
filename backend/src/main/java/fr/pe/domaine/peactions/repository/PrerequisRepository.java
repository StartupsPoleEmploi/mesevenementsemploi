package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.CategoriePrerequis;
import fr.pe.domaine.peactions.model.Prerequis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrerequisRepository extends JpaRepository<Prerequis, Long> {

    List<Prerequis> findAllByCategorie(CategoriePrerequis categoriePrerequis);

}
