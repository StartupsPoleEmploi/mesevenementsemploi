package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.commun.enumeration.ERole;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Etablissement;
import fr.pe.domaine.peactions.model.Role;
import fr.pe.domaine.peactions.repository.EtablissementRepository;
import fr.pe.domaine.peactions.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissementService {

    private static final Logger logger = LoggerFactory.getLogger(EtablissementService.class);
    @Autowired
    private EtablissementRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Etablissement> getAllEtablissements() {
        return repository.findAll();
    }

    public Etablissement getEtablissementById(long etablissementId) throws ResourceNotFoundException {
        logger.info("getEtablissementById for this id:: " + etablissementId);
        Etablissement etablissement = repository.findById(etablissementId).
                orElseThrow(() -> new ResourceNotFoundException("Etablissement not found for this id:: " + etablissementId));
        return etablissement;
    }

    public Etablissement getByCodeEtablissement(String codeEtablissement) throws ResourceNotFoundException {
        logger.info("getEtablissementById for this id:: " + codeEtablissement);
        Etablissement etablissement = repository.findByCodeEtablissement(codeEtablissement).
                orElseThrow(() -> new ResourceNotFoundException("Etablissement not found for this code:: " + codeEtablissement));
        return etablissement;
    }

    public List<Etablissement> getAllAgences() throws ResourceNotFoundException {

        Role role = roleRepository.findByName(ERole.ROLE_AGENCE).
                orElseThrow(() -> new ResourceNotFoundException("Role not found for this name:: " + ERole.ROLE_AGENCE));

        return repository.findEtablissementByRolesContains(role);
    }
}
