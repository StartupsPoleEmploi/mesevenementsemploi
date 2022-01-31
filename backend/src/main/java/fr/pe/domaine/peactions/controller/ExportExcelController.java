package fr.pe.domaine.peactions.controller;

import fr.pe.domaine.peactions.export.ExcelExporter;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.service.EtablissementService;
import fr.pe.domaine.peactions.service.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/export")
@CrossOrigin(origins = "*")
public class ExportExcelController {


    @Autowired
    private EvenementService evenementService;

    @Autowired
    private EtablissementService etablissementService;

    @Autowired
    private ExcelExporter excelExporter;

    @GetMapping("/evenements/all")
    public ResponseEntity<Resource> exportAllEvents(HttpServletResponse response) {

        return downloadFile(excelExporter.evenementsToExcel(evenementService.getListEvenements()), response);
    }

    @GetMapping("/evenements/agence/{codeAgence}")
    public ResponseEntity<Resource> exportEvenementsParAgence(@PathVariable(value = "codeAgence") String codeEtablissement, HttpServletResponse response) {
        List<Evenement> evenements = evenementService.getAllEvenementsByEtablissementWithoutPaginate(etablissementService.getByCodeEtablissement(codeEtablissement));
        return downloadFile(excelExporter.evenementsToExcel(evenements), response);
    }

    private ResponseEntity<Resource> downloadFile(byte[] bytes, HttpServletResponse response) {
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=evenements.xlsx");
        response.setHeader("filename", "evenements.xlsx");
        InputStream inputStream = new ByteArrayInputStream(bytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=evenements.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(new InputStreamResource(inputStream));
    }


}
