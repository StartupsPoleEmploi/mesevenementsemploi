package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.EvenementModaliteAcces;
import fr.pe.domaine.peactions.model.ModaliteAcces;
import fr.pe.domaine.peactions.repository.EvenementModaliteAccesRepository;
import fr.pe.domaine.peactions.repository.EvenementRepository;
import fr.pe.domaine.peactions.repository.ModaliteAccesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModaliteAccesService {

    private static final Logger logger = LoggerFactory.getLogger(ModaliteAccesService.class);
    @Autowired
    private ModaliteAccesRepository repository;

    @Autowired
    private EvenementModaliteAccesRepository evenementModaliteAccesRepository;


    @Autowired
    private EvenementRepository evenementRepository;

    public List<ModaliteAcces> getAllModaliteAccess() {
        return repository.findAll();
    }

    public ModaliteAcces getModaliteAccesById(long modaliteAccesId) throws ResourceNotFoundException {
        logger.info("getModaliteById for this id:: " + modaliteAccesId);
        ModaliteAcces modaliteAcces = repository.findById(modaliteAccesId).
                orElseThrow(() -> new ResourceNotFoundException("ModaliteAcces not found for this id:: " + modaliteAccesId));
        return modaliteAcces;
    }

    public List<EvenementModaliteAcces> getAllModaliteAccessByEvent(long eventIid) {
        logger.info("getAllModaliteAccessByEvent for this id:: " + eventIid);
        Evenement evenement = evenementRepository.findById(eventIid).
                orElseThrow(() -> new ResourceNotFoundException("Evenement not found for this id:: " + eventIid));
        return evenementModaliteAccesRepository.findAllByEvenement(evenement);
    }
}

