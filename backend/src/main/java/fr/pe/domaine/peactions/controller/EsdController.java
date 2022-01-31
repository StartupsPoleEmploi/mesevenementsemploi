package fr.pe.domaine.peactions.controller;

import fr.pe.domaine.peactions.emploistoredev.EsdClient;
import fr.pe.domaine.peactions.emploistoredev.ressources.OffreESD;
import fr.pe.domaine.peactions.emploistoredev.ressources.RomeESD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/esd")
@CrossOrigin(origins = "*")
public class EsdController {

    private static final Logger logger = LoggerFactory.getLogger(EsdController.class);

    @Autowired
    private EsdClient esdClient;


    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/romes")
    public ResponseEntity<List<RomeESD>> getAllRomes() {
        logger.info("Get all romes...");
        List<RomeESD> sortedList = esdClient.getRomes().stream().sorted(Comparator.comparing(RomeESD::getCode))
                .collect(Collectors.toList());
        return ResponseEntity.ok(sortedList);
    }

    @GetMapping("/rome/{code}")
    public ResponseEntity<RomeESD> getAllRome(@PathVariable(value = "code") String code) {
        logger.info("Get rome...");

        RomeESD romeESD = esdClient.getRome(code);
        return ResponseEntity.ok(romeESD);
    }


    @GetMapping("/offre/{id}")
    public ResponseEntity<OffreESD> getOffre(@PathVariable(value = "id") String id) {
        logger.info("Get offre for id..." + id);
        return ResponseEntity.ok(esdClient.getOffre(id));
    }


}
