package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.TypeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTagRepository extends JpaRepository<TypeTag, Long> {

}
