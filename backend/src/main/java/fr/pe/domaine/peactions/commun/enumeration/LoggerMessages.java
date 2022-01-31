package fr.pe.domaine.peactions.commun.enumeration;

public enum LoggerMessages {

    USER_INFO_KO("Erreur Technique : impossible de récupérer infos individu"),
    RETOUR_SERVICE_KO("Erreur Technique : erreur %s en retour du service %s."),
    SIMULATION_IMPOSSIBLE_CODE_AIDE_INEXISTANT("Erreur Technique : impossible d'effectuer la simulation car le code dl'aide n'exitse pas."),
    SIMULATION_IMPOSSIBLE_MONTANT_ASS_SIMULE_KO("Erreur Technique : impossible d'effectuer la simulation car les montants ASS simulés sur les premiers mois non présents."),
    SIMULATION_IMPOSSIBLE_PROBLEME_TECHNIQUE("Erreur technique : simulation impossible dûe à l'erreur : %s");

    private final String message;

    LoggerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
