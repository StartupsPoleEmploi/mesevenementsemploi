package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Candidat;
import fr.pe.domaine.peactions.repository.CandidatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidatService {

    private static final Logger logger = LoggerFactory.getLogger(CandidatService.class);
    @Autowired
    private CandidatRepository repository;


    public Candidat getCandidatById(long candidatId) throws ResourceNotFoundException {
        Candidat candidat = repository.findById(candidatId).
                orElseThrow(() -> new ResourceNotFoundException("Candidat not found for this id:: " + candidatId));
        return candidat;
    }

    public Candidat createCandidat(@RequestBody Candidat candidat) {
        logger.info("service create candidat");
        return repository.save(candidat);
    }


    public void deleteCandidat(long candidatId) throws ResourceNotFoundException {
        Candidat candidat = repository.findById(candidatId).
                orElseThrow(() -> new ResourceNotFoundException("Candidat not found for this id:: " + candidatId));
        repository.delete(candidat);
    }


    public List<Candidat> getAllCandidats(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        Page<Candidat> pagedResult = repository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Candidat>();
        }
    }

    public Candidat getCandidatsByIdentifiantCrypter(String identifiant) {
        logger.info("getCandidatsByIdentifiantCrypter for this id:: " + identifiant);
        Candidat candidat = repository.findByIdentifiantCrypter(identifiant);
        return candidat;
    }
}
