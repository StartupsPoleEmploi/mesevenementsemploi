package fr.pe.domaine.peactions.commun.enumeration;

public enum UnauthorizedMessages {

    ACCES_NON_AUTORISE_NONCE_INCORRECT("Accès à l'application non autorisée : nonce user différent du nonce retourné par endpoint access token Pôle emploi.");

    private final String message;

    UnauthorizedMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
