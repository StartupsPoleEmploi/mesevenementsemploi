package fr.pe.domaine.peactions.controller;

import fr.pe.domaine.peactions.dto.NafDto;
import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.Naf;
import fr.pe.domaine.peactions.service.NafService;
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
@RequestMapping("/naf")
@CrossOrigin(origins = "*")
public class NafController {

    private static final Logger logger = LoggerFactory.getLogger(NafController.class);

    @Autowired
    private NafService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<NafDto>> getAllNafs() {
        logger.info("Get all the nafs...");
        List<Naf> nafs = service.getAllNafs();


        List<NafDto> nafDtoList = new ArrayList<>();
        for (Naf naf : nafs) {
            nafDtoList.add(customMapper.map(naf, NafDto.class));
        }
        return ResponseEntity.ok(nafDtoList);
    }

    @GetMapping("/{code}")
    public ResponseEntity<NafDto> getNafById(@PathVariable(value = "code") String nafCode) throws ResourceNotFoundException {
        logger.info("Get naf by code...");
        return ResponseEntity.ok(customMapper.map(service.getNafByCode(nafCode), NafDto.class));
    }
}
