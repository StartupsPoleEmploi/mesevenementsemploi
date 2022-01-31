package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.ModaliteAcces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModaliteAccesRepository extends JpaRepository<ModaliteAcces, Long> {

}
