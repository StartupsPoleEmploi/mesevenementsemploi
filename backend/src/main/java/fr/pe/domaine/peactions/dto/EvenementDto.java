package fr.pe.domaine.peactions.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class EvenementDto {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Date dateISO;

    @Getter
    @Setter
    @ApiModelProperty(required = true, dataType = "date", example = "30-04-2021")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateEvenement;

    @Getter
    @Setter
    @JsonFormat(pattern = "HH:mm")
    @ApiModelProperty(required = true, dataType = "date-time", example = "10:00")
    private LocalTime heureDebut;

    @Getter
    @Setter
    @JsonFormat(pattern = "HH:mm")
    @ApiModelProperty(required = true, dataType = "date-time", example = "18:00")
    private LocalTime heureFin;

    @Getter
    @Setter
    @ApiModelProperty(notes = "Statut de l'événement", dataType = "string", example = "Statut de l'événement")
    private String statut;

    @Getter
    @Setter
    @ApiModelProperty(notes = "Description de l'événement", dataType = "string", example = "Description de l'événement")
    private String description;
    @Getter
    @Setter
    @ApiModelProperty(notes = "Déroulement de l'événement", dataType = "string", example = "Déroulement de l'événement")
    private String deroulement;

    @Getter
    @Setter
    @ApiModelProperty(notes = "Logo de l'événement", dataType = "base64String", example = "Logo de l'événement")
    private byte[] logo;

    @Getter
    @Setter
    @JsonProperty(value = "estApublier")
    private Boolean estApublier;

    @Getter
    @Setter
    private String timeZone;

    @Getter
    @Setter
    @ApiModelProperty(notes = "titre de l'événement", dataType = "string", example = "titre de l'événement")
    private String titre;

    @Getter
    @Setter
    @ApiModelProperty(notes = "adresse de l'événement", dataType = "string", example = "adresse de l'événement")
    private String adresse;

    @Getter
    @Setter
    @ApiModelProperty(notes = "code postal de l'événement", dataType = "string", example = "75001")
    private String codePostal;

    @Getter
    @Setter
    @ApiModelProperty(notes = "ville de l'événement", dataType = "string", example = "ville de l'événement")
    private String ville;

    @Getter
    @Setter
    @ApiModelProperty(notes = "code naf de l'événement", dataType = "string", example = "code naf de l'événement")
    private String nafCode;

    @Getter
    @Setter
    @ApiModelProperty(notes = "libellé naf de l'événement", dataType = "string", example = "libellé naf de l'événement")
    private String nafLibelle;


    @Getter
    @Setter
    @ApiModelProperty(notes = "etat de l'événement", dataType = "Long", example = "1")
    private Long etatId;

    @Getter
    @Setter
    @ApiModelProperty(notes = "code rome de l'événement", dataType = "string", example = "H2906")
    private String romeId;

    @Getter
    @Setter
    @ApiModelProperty(notes = "url lien en savoir plus", dataType = "string", example = "www.ensavoirplus.fr")
    private String lienPlusInformation;

    @Getter
    @Setter
    @ApiModelProperty(notes = "libelle du lien en savoir plus", dataType = "string", example = "En savoir plus sur le metier")
    private String libelleLienPlusInformation;

    @Getter
    @Setter
    private List<OffreDto> offres;

    @Getter
    @Setter
    private List<IntervenantDto> intervenants;

    @Getter
    @Setter
    private List<TagDto> tags;

    @Getter
    @Setter
    private List<PrerequisDto> prerequis;
    @Getter
    @Setter
    private List<ModeAccees> modeAcceesList;

    @Getter
    @Setter
    private String codeEtablissement;

    @Getter
    @Setter
    private String libelleEtablissement;
}
