package fr.pe.domaine.peactions.controller;


import fr.pe.domaine.peactions.dto.CategoriePrerequisDto;
import fr.pe.domaine.peactions.dto.PrerequisDto;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.CategoriePrerequis;
import fr.pe.domaine.peactions.model.Prerequis;
import fr.pe.domaine.peactions.service.PrerequisService;
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
@RequestMapping("/prerequis")
@CrossOrigin(origins = "*")
public class PrerequisController {

    private static final Logger logger = LoggerFactory.getLogger(PrerequisController.class);

    @Autowired
    private PrerequisService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<PrerequisDto>> getAllPrerequis() {
        logger.info("Get all the prerequiss...");
        List<Prerequis> prerequiss = service.getAllPrerequis();

        List<PrerequisDto> prerequisDtoList = new ArrayList<>();
        for (Prerequis prerequis : prerequiss) {
            prerequisDtoList.add(customMapper.prerequisToPrerequisDto(prerequis));
        }

        return ResponseEntity.ok(prerequisDtoList);
    }

    @GetMapping("/all/{categorieId}")
    public ResponseEntity<List<PrerequisDto>> getAllPrerequisByCategorie(@PathVariable(name = "categorieId") Long categorieId) throws ResourceNotFoundException {
        logger.info("Get all the prerequis by categorie...");
        List<Prerequis> prerequiss = service.getAllPrerequisByCategorie(categorieId);

        List<PrerequisDto> prerequisDtoList = new ArrayList<>();
        for (Prerequis prerequis : prerequiss) {
            prerequisDtoList.add(customMapper.prerequisToPrerequisDto(prerequis));
        }

        return ResponseEntity.ok(prerequisDtoList);
    }

    @GetMapping("categorie/all")
    public ResponseEntity<List<CategoriePrerequisDto>> getAllCategoriePrerequis() {
        logger.info("Get all categorie prerequiss...");
        List<CategoriePrerequis> categoriePrerequiss = service.getAllCategoriePrerequis();

        List<CategoriePrerequisDto> categoriePrerequisDtoList = new ArrayList<>();
        for (CategoriePrerequis categoriePrerequis : categoriePrerequiss) {
            categoriePrerequisDtoList.add(customMapper.map(categoriePrerequis, CategoriePrerequisDto.class));
        }

        return ResponseEntity.ok(categoriePrerequisDtoList);
    }
}
