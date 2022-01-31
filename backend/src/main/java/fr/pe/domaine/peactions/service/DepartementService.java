package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Departement;
import fr.pe.domaine.peactions.repository.DepartementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    private static final Logger logger = LoggerFactory.getLogger(DepartementService.class);
    @Autowired
    private DepartementRepository repository;


    public List<Departement> getAllDepartements() {
        return repository.findAllByOrderByLibelle();
    }


    public Departement getDepartementByCode(String departementCode) throws ResourceNotFoundException {
        logger.info("getDepartementByCode for this id:: " + departementCode);

        Departement departement = repository.findById(departementCode).
                orElseThrow(() -> new ResourceNotFoundException("Departement not found for this id:: " + departementCode));
        return departement;
    }

}
