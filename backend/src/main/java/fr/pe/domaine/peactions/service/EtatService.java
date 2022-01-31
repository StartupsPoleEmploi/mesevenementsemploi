package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Etat;
import fr.pe.domaine.peactions.repository.EtatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatService {

    private static final Logger logger = LoggerFactory.getLogger(EtatService.class);
    @Autowired
    private EtatRepository repository;


    public List<Etat> getAllEtats() {
        return repository.findAll();
    }


    public Etat getEtatById(long etatId) throws ResourceNotFoundException {
        logger.info("getEtatById for this id:: " + etatId);

        Etat etat = repository.findById(etatId).
                orElseThrow(() -> new ResourceNotFoundException("Etat not found for this id:: " + etatId));
        return etat;
    }

}
