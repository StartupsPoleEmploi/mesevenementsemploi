package fr.pe.domaine.peactions.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.pe.domaine.peactions.payload.PeConnectAuthorization;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
public class CandidatDto {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String identifiantRci;
    @Getter
    @Setter
    private String identifiantCrypter;
    @Getter
    @Setter
    private String civilite;
    @Getter
    @Setter
    private String nom;
    @Getter
    @Setter
    private String prenom;
    @Getter
    @Setter
    @JsonFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(dataType = "date", example = "30-04-1980")
    private Date dateNaissance;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private boolean present;
    @Getter
    @Setter
    private Long modaliteAccesId;

    @Getter
    @Setter
    private Integer statutInscription;

    @Getter
    @Setter
    private PeConnectAuthorization peConnectAuthorization;
}
