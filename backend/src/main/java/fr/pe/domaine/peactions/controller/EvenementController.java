package fr.pe.domaine.peactions.controller;

import fr.pe.domaine.peactions.dto.EvenementDto;
import fr.pe.domaine.peactions.exception.InvalidEventException;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.service.EtablissementService;
import fr.pe.domaine.peactions.service.EvenementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/evenement")
@CrossOrigin(origins = "*")
public class EvenementController {

    private static final Logger logger = LoggerFactory.getLogger(EvenementController.class);

    @Autowired
    private EvenementService service;
    @Autowired
    private EtablissementService etablissementService;
    @Autowired
    private CustomMapper customMapper;

    @GetMapping("/all")
    public ResponseEntity<List<EvenementDto>> getAllEvenements(
            @RequestParam(required = false) String etat,
            @RequestParam(required = false) String dept,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "dateEvenement") String sortBy) {
        List<Evenement> list = service.getAllEvenements(etat, dept, pageNo, pageSize, sortBy);
        return getListEvtDtoResponseEntity(list);
    }

    @GetMapping("/all/agence/{codeAgence}")
    public ResponseEntity<List<EvenementDto>> getAllEvenementsByAgencePaginate(
            @PathVariable(value = "codeAgence") String codeEtablissement,
            @RequestParam(required = false) String dept,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "dateEvenement") String sortBy) throws ResourceNotFoundException {
        List<Evenement> list = service.getAllEvenementsByEtablissement(etablissementService.getByCodeEtablissement(codeEtablissement), pageNo, pageSize, sortBy);
        return getListEvtDtoResponseEntity(list);
    }

    @GetMapping("/all/departement/{codeDepartement}")
    public ResponseEntity<List<EvenementDto>> getAllEvenementsByDepartement(
            @PathVariable(value = "codeDepartement") String codeDepartement)
            throws ResourceNotFoundException {
        List<Evenement> list = service.getAllEvenementsByDepartement(codeDepartement);
        return getListEvtDtoResponseEntity(list);
    }

    private ResponseEntity<List<EvenementDto>> getListEvtDtoResponseEntity(
            List<Evenement> list) {
        List<EvenementDto> evenementDtoList = new ArrayList<>();
        for (Evenement evenement : list) {
            evenementDtoList.add(customMapper.evenementToEvenementDto(evenement));
        }
        return new ResponseEntity<>(evenementDtoList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<EvenementDto> getEvenementById(
            @PathVariable(value = "id") long evenementId)
            throws ResourceNotFoundException {
        logger.info("Get evenement by id...");
        return ResponseEntity.ok(customMapper.evenementToEvenementDto(service.getEvenementById(evenementId)));

    }

    @PostMapping("")
    @PreAuthorize("hasRole('AGENCE') or hasRole('SUPERVISEUR') or hasRole('ADMIN')")
    public ResponseEntity<EvenementDto> createEvenement(
            @RequestBody EvenementDto evenementDto)
            throws ResourceNotFoundException, InvalidEventException {
        logger.info("Controller Insert evenement...");
        Evenement evenement = service.createEvenement(customMapper.evenementDtoToEvenement(evenementDto));
        return ResponseEntity.ok(customMapper.evenementToEvenementDto(evenement));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AGENCE') or hasRole('SUPERVISEUR') or hasRole('ADMIN')")
    public ResponseEntity<EvenementDto> updateEvenementById(
            @PathVariable(value = "id") long evenementId,
            @RequestBody EvenementDto updatedEvenementDto)
            throws ResourceNotFoundException, InvalidEventException {
        logger.info("Update evenement...");
        updatedEvenementDto.setId(evenementId);
        Evenement evenement = customMapper.evenementDtoToEvenement(updatedEvenementDto);
        Evenement updatedEvenement = service.createEvenement(evenement);
        return ResponseEntity.ok(customMapper.evenementToEvenementDto(updatedEvenement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvenement(
            @PathVariable(value = "id") long evenementId)
            throws ResourceNotFoundException {
        logger.info("Delete evenement...");
        service.deleteEvenement(evenementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/agence/{codeAgence}")
    public ResponseEntity<Map<String, Object>> getCountEvenementsByAgence(
            @PathVariable(value = "codeAgence") String codeEtablissement)
            throws ResourceNotFoundException {

        Long count = service.getCountEvenementsByEtablissement(etablissementService.getByCodeEtablissement(codeEtablissement));
        Map<String, Object> payload = new HashMap<>();
        payload.put("count", count);
        return ResponseEntity.ok(payload);
    }

    @GetMapping(value = "/count/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllCount(
            @RequestParam(required = false) String dept,
            @RequestParam(required = false) String etat)
            throws ResourceNotFoundException {
        Long count = service.getCountAll(etat,dept);
        Map<String, Object> payload = new HashMap<>();
        payload.put("count", count);
        return ResponseEntity.ok(payload);
    }

    @GetMapping("/all/back/sorted")
    public ResponseEntity<List<EvenementDto>> getAllBackEventsSorted(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) throws ResourceNotFoundException {
        List<EvenementDto> evenementDtoList = new ArrayList<>();
        for (Evenement evenement: service.getBackEventsPagesOrderByStatus(page, size)) {
            evenementDtoList.add(customMapper.evenementToEvenementDto(evenement));
        }

        return new ResponseEntity<>(evenementDtoList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/all/back/sorted/agence/{codeAgence}")
    public ResponseEntity<List<EvenementDto>> getAllEventsSortedByAgency(
            @PathVariable(value = "codeAgence") String codeEtablissement,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) throws ResourceNotFoundException {
        List<EvenementDto> evenementDtoList = new ArrayList<>();
        for (Evenement evenement: service.getBackEventsPagesOrderByStatusAndByAgency(codeEtablissement, page, size)) {
            evenementDtoList.add(customMapper.evenementToEvenementDto(evenement));
        }
        return new ResponseEntity<>(evenementDtoList, new HttpHeaders(), HttpStatus.OK);
    }

}
