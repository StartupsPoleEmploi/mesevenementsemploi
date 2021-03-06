package fr.pe.domaine.peactions.utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class DateUtile {

    public static final ZoneId ZONE_ID_FRANCE = ZoneId.of("Europe/Paris");
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public int getNbrMoisEntreDeuxDates(Date dateDebut, Date dateFin) {
        return (int) ChronoUnit.MONTHS.between(dateDebut.toInstant().atZone(ZONE_ID_FRANCE).toLocalDate(), dateFin.toInstant().atZone(ZONE_ID_FRANCE).toLocalDate());
    }

    public LocalDate getDatePremierJourDuMois(LocalDate dateCourante) {
        YearMonth yearMonth = YearMonth.of(dateCourante.getYear(), dateCourante.getMonthValue());
        return yearMonth.atDay(1);
    }

    public LocalDate getDateDernierJourDuMois(LocalDate dateCourante) {
        YearMonth yearMonth = YearMonth.of(dateCourante.getYear(), dateCourante.getMonthValue());
        return yearMonth.atEndOfMonth();
    }

    public LocalDate getDateJour() {
        return LocalDate.now(ZONE_ID_FRANCE);
    }

    public String getMonthFromLocalDate(LocalDate localDate) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(Double.valueOf(localDate.getMonthValue()));
    }

    public LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZONE_ID_FRANCE).toLocalDate();
    }


    public String convertDateToString(LocalDate dateToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return formatter.format(dateToConvert);
    }

    public String convertDateToString(LocalDate dateToConvert, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return formatter.format(dateToConvert);
    }

    public LocalDate ajouterMoisALocalDate(LocalDate localDate, int nombreMoisAAjouter) {
        return localDate.plusMonths(nombreMoisAAjouter);
    }

    public LocalDate enleverMoisALocalDate(LocalDate localDate, int nombreMoisAEnlever) {
        return localDate.minusMonths(nombreMoisAEnlever);
    }

    public boolean isDateAvant(LocalDate dateToCheck, LocalDate dateLimite) {
        return dateToCheck.isBefore(dateLimite);
    }

    public int getNombreJoursDansLeMois(LocalDate date) {
        YearMonth yearMonthObject = YearMonth.of(date.getYear(), date.getMonthValue());
        return yearMonthObject.lengthOfMonth();
    }

    public int getAge(LocalDate dateNaissance) {
        if ((dateNaissance != null)) {
            return Period.between(dateNaissance, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}
