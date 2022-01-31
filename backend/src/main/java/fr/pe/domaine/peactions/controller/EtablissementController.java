package fr.pe.domaine.peactions.controller;


import fr.pe.domaine.peactions.dto.EtablissementDto;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.Etablissement;
import fr.pe.domaine.peactions.service.EtablissementService;
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
@RequestMapping("/etablissement")
@CrossOrigin(origins = "*")
public class EtablissementController {

    private static final Logger logger = LoggerFactory.getLogger(EtablissementController.class);

    @Autowired
    private EtablissementService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<EtablissementDto>> getAllEtablissements() {
        logger.info("Get all the etablissements...");
        List<Etablissement> etablissements = service.getAllEtablissements();


        List<EtablissementDto> etablissementDtoList = new ArrayList<>();
        for (Etablissement etablissement : etablissements) {
            etablissementDtoList.add(customMapper.etablissementToEtablissementDto(etablissement));
        }

        return ResponseEntity.ok(etablissementDtoList);
    }

    @GetMapping("/agence/all")
    public ResponseEntity<List<EtablissementDto>> getAllAgences() throws ResourceNotFoundException {
        logger.info("Get all the agences...");
        List<Etablissement> etablissements = service.getAllAgences();
        List<EtablissementDto> etablissementDtoList = new ArrayList<>();
        for (Etablissement etablissement : etablissements) {
            etablissementDtoList.add(customMapper.etablissementToEtablissementDto(etablissement));
        }

        return ResponseEntity.ok(etablissementDtoList);
    }

    @GetMapping("/{codeEtablissement}")
    public ResponseEntity<EtablissementDto> getEtablissementByCodeEtablissement(@PathVariable(value = "codeEtablissement") String codeEtablissement) throws ResourceNotFoundException {
        logger.info("Get etablissement by codeEtablissement...");
        return ResponseEntity.ok(customMapper.map(service.getByCodeEtablissement(codeEtablissement), EtablissementDto.class));
    }
}
