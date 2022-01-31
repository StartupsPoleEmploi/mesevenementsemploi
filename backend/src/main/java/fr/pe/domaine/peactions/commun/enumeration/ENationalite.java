package fr.pe.domaine.peactions.commun.enumeration;

public enum ENationalite {

    FRANCAISE("Française"),
    EUROPEEN_OU_SUISSE("Ressortissant européen ou suisse"),
    AUTRE("Autre");

    private final String valeur;

    ENationalite(String valeur) {
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }
}
