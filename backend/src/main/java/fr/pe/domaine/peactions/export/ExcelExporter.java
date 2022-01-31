package fr.pe.domaine.peactions.export;

import fr.pe.domaine.peactions.emploistoredev.EsdClient;
import fr.pe.domaine.peactions.emploistoredev.ressources.RomeESD;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.Naf;
import fr.pe.domaine.peactions.service.NafService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class ExcelExporter {


    private static final int COL_DATE_EVENEMENT = 0;
    private static final int COL_HEURE_DEBUT = 1;
    private static final int COL_HEURE_FIN = 2;
    private static final int COL_ETAT = 3;
    private static final int COL_VISIBLITE = 4;
    private static final int COL_STATUT = 5;
    private static final int COL_DESCRIPTIF = 6;
    private static final int COL_DEROULE = 7;
    private static final int COL_TIMEZONE = 8;
    private static final int COL_TITRE = 9;
    private static final int COL_ADRESSE = 10;
    private static final int COL_CODE_POSTAL = 11;
    private static final int COL_VILLE = 12;
    private static final int COL_URL_EVT = 13;
    private static final int COL_NAF = 14;
    private static final int COL_ROME = 15;
    private static final int COL_RECRUTEURS = 16;
    private static final int COL_TYPE = 17;
    private static final int COL_PREREQUIS = 18;
    private static final int COL_PARTICIPATION_SUR_PLACE = 19;
    private static final int COL_NB_INSCRITS_SUR_PLACE = 20;
    private static final int COL_NB_PLACE_SUR_PLACE = 21;
    private static final int COL_PARTICIPATION_EN_LIGNE = 22;
    private static final int COL_NB_INSCRITS_EN_LIGNE = 23;
    private static final int COL_NB_PLACE_EN_LIGNE = 24;
    private static final int COL_OBJECTIF = 25;
    private static final int COL_OPERATIONS = 26;
    private static final int COL_BENEFICE_PARTICIPATION = 27;
    private static final int COL_PUBLIC_CIBLE = 28;
    private static final int COL_CODE_SAFIR = 29;
    private static final int COL_AGENCE = 30;
    private static final int COL_LIEN_DIRECT = 31;
    private static final int COL_DATE_CREATION = 32;
    static String[] EvenementHeaders = {"Date d'evenement", "Heure début", "Heure fin", "etat", "visibilité", "statut", "Description", "Déroulé", "Timezone", "Titre d'evenement",
            "adresse d'evenement", "Code postal", "ville", "url de l'evenement", "NAF", "ROME", "Recruteur(s) / Intervenant(s)", "Type d'evenement", "Prérequis"
            , "Participation \"SUR PLACE\"", "Nb d'inscrits \"SUR PLACE\"", "Nb de place total \"SUR PLACE\"", "Participation \"EN LIGNE\"", "Nb d'inscrits \"EN LIGNE\""
            , "Nb de place total \"EN LIGNE\"", "Objectif", "Opérations", "Bénéfice de participation", "Public cible", "Code Safir agence", "Agence", "Lien Direct", "Date de création"};

    @Value("${peactions.candidat.url}")
    private String siteCandidatUrl;
    static String SHEET = "Evenements";
    @Autowired
    private NafService nafService;
    @Autowired
    private EsdClient esdClient;

    public byte[] evenementsToExcel(List<Evenement> evenements) {

        List<RomeESD> romeESDList = esdClient.getRomes();
        List<Naf> nafList = nafService.getAllNafs();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);
            XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
            style.setWrapText(true);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(new XSSFColor(java.awt.Color.decode("#4833E3"), new DefaultIndexedColorMap()));
            for (int col = 0; col < EvenementHeaders.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(EvenementHeaders[col]);
                cell.setCellStyle(style);
            }

            int rowIdx = 1;
            for (Evenement evenement : evenements) {
            	addRowForEvenement(workbook, sheet, evenement, romeESDList, nafList, rowIdx);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    
    public void addRowForEvenement(Workbook workbook, Sheet sheet, Evenement evenement, List<RomeESD> romeESDList, List<Naf> nafList, int rowIndex) {
    	Row row = sheet.createRow(rowIndex++);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat poiFormat = workbook.createDataFormat();
        String excelFormatPattern = DateFormatConverter.convert(Locale.FRANCE, "dd-MM-yy");
        cellStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));
        if (evenement.getDateEvenement() != null) {
            Cell dateCell = row.createCell(COL_DATE_EVENEMENT);
            dateCell.setCellValue(Date.from(evenement.getDateEvenement().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            dateCell.setCellStyle(cellStyle);
        }
        if (evenement.getHeureDebut() != null) {
            row.createCell(COL_HEURE_DEBUT).setCellValue(evenement.getHeureDebut().toString());
        }
        if (evenement.getHeureFin() != null) {
            row.createCell(COL_HEURE_FIN).setCellValue(evenement.getHeureFin().toString());
        }
        row.createCell(COL_ETAT).setCellValue(evenement.getEtat().getLibelle());
        row.createCell(COL_VISIBLITE).setCellValue(evenement.getEstApublier() != null && evenement.getEstApublier() ? "OUI" : "NON");
        row.createCell(COL_STATUT).setCellValue(evenement.getStatut());
        row.createCell(COL_DESCRIPTIF).setCellValue(evenement.getDescription());
        row.createCell(COL_DEROULE).setCellValue(evenement.getDeroulement());
        row.createCell(COL_TIMEZONE).setCellValue(evenement.getTimeZone());
        row.createCell(COL_TITRE).setCellValue(evenement.getTitre());
        row.createCell(COL_ADRESSE).setCellValue(evenement.getAdresse());
        row.createCell(COL_CODE_POSTAL).setCellValue(evenement.getCodePostal());
        row.createCell(COL_VILLE).setCellValue(evenement.getVille());
        row.createCell(COL_URL_EVT).setCellValue(evenement.urlEnligne());

        if (evenement.getNafCode() != null) {
            Naf naf = nafList.stream().filter(naf1 -> naf1.getCode().equals(evenement.getNafCode())).findFirst().orElse(null);

            row.createCell(COL_NAF).setCellValue(naf != null ? naf.getCode().concat(" ").concat(naf.getLibelle()) : "");
        }

        if (isRomeValid(evenement, romeESDList)) {
            RomeESD rome = romeESDList.stream().filter(romeESD -> romeESD.getCode().equals(evenement.getRomeId())).findFirst().orElse(null);

            row.createCell(COL_ROME).setCellValue(rome != null ? rome.getCode().concat(" ").concat(rome.getLibelle()) : "");
        }
        row.createCell(COL_RECRUTEURS).setCellValue(evenement.getIntervenants().toString());
        row.createCell(COL_TYPE).setCellValue(evenement.getType());
        row.createCell(COL_PREREQUIS).setCellValue(evenement.getPrerequis());
        row.createCell(COL_PARTICIPATION_SUR_PLACE).setCellValue(evenement.isSurPlace());
        row.createCell(COL_NB_INSCRITS_SUR_PLACE).setCellValue(evenement.nbInscritSurPlace());
        row.createCell(COL_NB_PLACE_SUR_PLACE).setCellValue(evenement.nbPlaceSurPlace());
        row.createCell(COL_PARTICIPATION_EN_LIGNE).setCellValue(evenement.isEnLigne());
        row.createCell(COL_NB_INSCRITS_EN_LIGNE).setCellValue(evenement.nbInscritEnLigne());
        row.createCell(COL_NB_PLACE_EN_LIGNE).setCellValue(evenement.nbPlaceEnLigne());
        row.createCell(COL_OBJECTIF).setCellValue(evenement.getObjectifs());
        row.createCell(COL_OPERATIONS).setCellValue(evenement.getOperations());
        row.createCell(COL_BENEFICE_PARTICIPATION).setCellValue(evenement.getBenefices());
        row.createCell(COL_PUBLIC_CIBLE).setCellValue(evenement.getPublicCibles());
        if (evenement.getEtablissement() != null) {
            row.createCell(COL_CODE_SAFIR).setCellValue(evenement.getEtablissement().getCodeEtablissement());
            row.createCell(COL_AGENCE).setCellValue(evenement.getEtablissement().getLibelle());
        }
        row.createCell(COL_LIEN_DIRECT).setCellValue(siteCandidatUrl.concat("/evenement/").concat(String.valueOf(evenement.getId())));

        Cell dateCell = row.createCell(COL_DATE_CREATION);
        dateCell.setCellValue(evenement.getCreatedOn());
        dateCell.setCellStyle(cellStyle);
    	
    }
    
    private boolean isRomeValid(Evenement evenement, List<RomeESD>romeESDList) {
    	return evenement.getRomeId() != null && romeESDList != null && !romeESDList.isEmpty();
    	
    }


}
