package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Tag;
import fr.pe.domaine.peactions.model.TypeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByTypeTag(TypeTag typeTag);

}
