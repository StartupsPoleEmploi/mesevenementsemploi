package fr.pe.domaine.peactions.controller;


import fr.pe.domaine.peactions.dto.DepartementDto;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.Departement;
import fr.pe.domaine.peactions.service.DepartementService;
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
@RequestMapping("/departement")
@CrossOrigin(origins = "*")
public class DepartementController {

    private static final Logger logger = LoggerFactory.getLogger(DepartementController.class);

    @Autowired
    private DepartementService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        logger.info("Get all the departements...");
        List<Departement> departements = service.getAllDepartements();


        List<DepartementDto> departementDtoList = new ArrayList<>();
        for (Departement departement : departements) {
            departementDtoList.add(customMapper.map(departement, DepartementDto.class));
        }

        return ResponseEntity.ok(departementDtoList);
    }

    @GetMapping("/{code}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable(value = "code") String departementCode) throws ResourceNotFoundException {
        logger.info("Get departement by code...");
        return ResponseEntity.ok(customMapper.map(service.getDepartementByCode(departementCode), DepartementDto.class));
    }
}
