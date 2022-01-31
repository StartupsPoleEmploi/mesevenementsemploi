package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, String> {

    List<Departement> findAllByOrderByLibelle();
}
