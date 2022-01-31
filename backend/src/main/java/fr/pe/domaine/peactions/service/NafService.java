package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Naf;
import fr.pe.domaine.peactions.repository.NafRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NafService {

    private static final Logger logger = LoggerFactory.getLogger(NafService.class);
    @Autowired
    private NafRepository repository;


    public List<Naf> getAllNafs() {
        return repository.findAll();
    }


    public Naf getNafByCode(String nafCode) throws ResourceNotFoundException {
        logger.info("getNafByCode for this id:: " + nafCode);
        if (nafCode != null) {
            return repository.findById(nafCode).orElseThrow(() -> new ResourceNotFoundException("Naf not found for this code:: " + nafCode));
        }
        return null;
    }
}
