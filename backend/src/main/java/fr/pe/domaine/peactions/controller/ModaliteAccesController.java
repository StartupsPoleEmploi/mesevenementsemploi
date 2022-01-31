package fr.pe.domaine.peactions.controller;


import fr.pe.domaine.peactions.dto.ModaliteAccesDto;
import fr.pe.domaine.peactions.dto.ModeAccees;
import fr.pe.domaine.peactions.mapper.CustomMapper;
import fr.pe.domaine.peactions.model.EvenementModaliteAcces;
import fr.pe.domaine.peactions.model.ModaliteAcces;
import fr.pe.domaine.peactions.service.ModaliteAccesService;
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
@RequestMapping("/modaliteAcces")
@CrossOrigin(origins = "*")
public class ModaliteAccesController {

    private static final Logger logger = LoggerFactory.getLogger(ModaliteAccesController.class);

    @Autowired
    private ModaliteAccesService service;

    @Autowired
    private CustomMapper customMapper;


    @GetMapping("/all")
    public ResponseEntity<List<ModaliteAccesDto>> getAllModaliteAccess() {
        logger.info("Get all the modaliteAccess...");
        List<ModaliteAcces> modaliteAccess = service.getAllModaliteAccess();


        List<ModaliteAccesDto> modaliteAccesDtoList = new ArrayList<>();
        for (ModaliteAcces modaliteAcces : modaliteAccess) {
            modaliteAccesDtoList.add(customMapper.map(modaliteAcces, ModaliteAccesDto.class));
        }

        return ResponseEntity.ok(modaliteAccesDtoList);
    }

    @GetMapping("all/evenement/{id}")
    public ResponseEntity<List<ModeAccees>> getAllModaliteAccessByEvent(@PathVariable(value = "id") long eventIid) {
        logger.info("Get all the modaliteAccess by event...");
        List<EvenementModaliteAcces> modaliteAccess = service.getAllModaliteAccessByEvent(eventIid);

        List<ModeAccees> modaliteAccesDtoList = new ArrayList<>();
        for (EvenementModaliteAcces evenementModaliteAcces : modaliteAccess) {
            ModeAccees modeAccees = new ModeAccees();
            ModaliteAccesDto modaliteAccesDto = new ModaliteAccesDto(evenementModaliteAcces.getModaliteAcces().getId(), evenementModaliteAcces.getModaliteAcces().getLibelle());
            modeAccees.setNombrePlace(evenementModaliteAcces.getNombrePlace());
            modeAccees.setUrlParticipation(evenementModaliteAcces.getUrlAcces());
            modeAccees.setModaliteAcces(modaliteAccesDto);
            modaliteAccesDtoList.add(modeAccees);
        }

        return ResponseEntity.ok(modaliteAccesDtoList);
    }

}
