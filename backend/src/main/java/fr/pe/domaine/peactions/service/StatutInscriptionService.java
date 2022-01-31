package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.StatutInscription;
import fr.pe.domaine.peactions.repository.StatutInscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatutInscriptionService {

    private static final Logger logger = LoggerFactory.getLogger(StatutInscriptionService.class);
    @Autowired
    private StatutInscriptionRepository repository;


    public List<StatutInscription> getAllStatutInscriptions() {
        return repository.findAll();
    }


    public StatutInscription getStatutInscriptionById(int statutInscriptionId) throws ResourceNotFoundException {
        logger.info("getStatutInscriptionById for this id:: " + statutInscriptionId);

        StatutInscription statutInscription = repository.findById(statutInscriptionId).
                orElseThrow(() -> new ResourceNotFoundException("StatutInscription not found for this id:: " + statutInscriptionId));
        return statutInscription;
    }

}
