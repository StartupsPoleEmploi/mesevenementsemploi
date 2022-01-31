package fr.pe.domaine.peactions.payload;

import fr.pe.domaine.peactions.emploistoredev.ressources.UserInfoESD;
import lombok.Getter;
import lombok.Setter;

public class Individu {

    @Getter
    @Setter
    private UserInfoESD userInfoESD;

    @Getter
    @Setter
    private PeConnectAuthorization peConnectAuthorization;

    @Getter
    @Setter
    private boolean isPopulationAutorisee;

    @Override
    public String toString() {
        return "Individu{" +
                "userInfoESD=" + userInfoESD +
                ", peConnectAuthorization=" + peConnectAuthorization +
                ", isPopulationAutorisee=" + isPopulationAutorisee +
                '}';
    }
}
