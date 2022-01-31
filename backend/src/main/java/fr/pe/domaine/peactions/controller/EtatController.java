package fr.pe.domaine.peactions.controller;


import fr.pe.domaine.peactions.dto.EtatDto;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.Etat;
import fr.pe.domaine.peactions.service.EtatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/etat")
@CrossOrigin(origins = "*")
public class EtatController {

    private static final Logger logger = LoggerFactory.getLogger(EtatController.class);

    @Autowired
    private EtatService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<EtatDto>> getAllEtats() {
        logger.info("Get all the etats...");
        List<Etat> etats = service.getAllEtats();


        List<EtatDto> etatDtoList = new ArrayList<>();
        for (Etat etat : etats) {
            etatDtoList.add(customMapper.map(etat, EtatDto.class));
        }

        return ResponseEntity.ok(etatDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtatDto> getEtatById(@PathVariable(value = "id") long etatId) throws ResourceNotFoundException {
        logger.info("Get etat by id...");
        return ResponseEntity.ok(customMapper.map(service.getEtatById(etatId), EtatDto.class));
    }
}
