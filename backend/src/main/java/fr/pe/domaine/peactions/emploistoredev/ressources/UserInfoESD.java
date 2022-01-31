package fr.pe.domaine.peactions.emploistoredev.ressources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class UserInfoESD {

    @Getter
    @Setter
    private String sub;

    @Getter
    @Setter
    private String gender;

    @JsonProperty("family_name")
    @Getter
    @Setter
    private String familyName;

    @JsonProperty("given_name")
    @Getter
    @Setter
    private String givenName;

    @Getter
    @Setter
    private String email;


    @Override
    public String toString() {
        return "UserInfoRessource [sub=" + sub + ", gender=" + gender + ", familyName=" + familyName + ", givenName="
                + givenName + ", email=" + email + "]";
    }
}
