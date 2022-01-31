package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Etablissement;
import fr.pe.domaine.peactions.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
    Optional<Etablissement> findByCodeEtablissement(String codeEtablissement);

    Boolean existsByCodeEtablissement(String codeEtablissement);

    Boolean existsByLibelle(String libelle);

    List<Etablissement> findEtablissementByRolesContains(Role role);

}
