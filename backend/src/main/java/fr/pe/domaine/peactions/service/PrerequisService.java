package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.CategoriePrerequis;
import fr.pe.domaine.peactions.model.Prerequis;
import fr.pe.domaine.peactions.model.PrerequisEvenement;
import fr.pe.domaine.peactions.model.PrerequisEvenementKey;
import fr.pe.domaine.peactions.repository.CategogiePrerequisRepository;
import fr.pe.domaine.peactions.repository.PrerequisEvenementRepository;
import fr.pe.domaine.peactions.repository.PrerequisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrerequisService {

    private static final Logger logger = LoggerFactory.getLogger(PrerequisService.class);
    @Autowired
    private PrerequisRepository repository;

    @Autowired
    private PrerequisEvenementRepository prerequisEvenementRepository;

    @Autowired
    private CategogiePrerequisRepository categogiePrerequisRepository;

    public List<Prerequis> getAllPrerequis() {
        return repository.findAll();
    }

    public Prerequis getPrerequisById(long prerequisId) throws ResourceNotFoundException {
        logger.info("getPrerequisById for this id:: " + prerequisId);
        Prerequis prerequis = repository.findById(prerequisId).
                orElseThrow(() -> new ResourceNotFoundException("Prerequis not found for this id:: " + prerequisId));

        return prerequis;
    }

    public CategoriePrerequis getCategoriePrerequisById(long categorieId) throws ResourceNotFoundException {
        logger.info("getCategoriePrerequisById for this id:: " + categorieId);
        CategoriePrerequis categoriePrerequis = categogiePrerequisRepository.findById(categorieId).
                orElseThrow(() -> new ResourceNotFoundException("CategoriePrerequis  not found for this id:: " + categorieId));

        return categoriePrerequis;
    }

    public List<CategoriePrerequis> getAllCategoriePrerequis() {
        return categogiePrerequisRepository.findAll();
    }

    public List<Prerequis> getAllPrerequisByCategorie(Long categorieId) throws ResourceNotFoundException {
        CategoriePrerequis categoriePrerequis = this.getCategoriePrerequisById(categorieId);
        return repository.findAllByCategorie(categoriePrerequis);
    }

    public PrerequisEvenement getPrerequisEvenementById(PrerequisEvenementKey key) {
        PrerequisEvenement prerequisEvenement = prerequisEvenementRepository.findById(key).
                orElseThrow(() -> new ResourceNotFoundException("PrerequisEvenement  not found for this key :: " + key));
        return prerequisEvenement;
    }
}
