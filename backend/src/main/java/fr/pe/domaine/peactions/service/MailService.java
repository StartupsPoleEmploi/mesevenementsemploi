package fr.pe.domaine.peactions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pe.domaine.peactions.commun.enumeration.EModaliteAcces;
import fr.pe.domaine.peactions.model.Candidat;
import fr.pe.domaine.peactions.model.CandidatEvenement;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.ModaliteAcces;
import fr.pe.domaine.peactions.payload.mailjet.Email;
import fr.pe.domaine.peactions.payload.mailjet.Message;
import fr.pe.domaine.peactions.payload.mailjet.Personne;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    ObjectMapper mapper = new ObjectMapper();
    @Value("${peactions.candidat.url}")
    private String siteCandidatUrl;
    @Value("${mailjet.apiSecretKey}")
    private String mailjetApiSecretKey;
    @Value("${mailjet.apiKey}")
    private String mailjetApiKey;
    @Value("${environement.production}")
    private Boolean environnementProduction;
    @Value("${mail.to.redirect}")
    private String mailToRedirect;
    @Value("${mailjet.id.template.email.confirmation}")
    private long idTemplateEmailConfirmation;
    @Value("${mailjet.id.template.email.confirmation.distance.sans.url}")
    private long idTemplateEmailConfirmationDistanceSansUrl;
    @Value("${mailjet.id.template.email.confirmation.distance.avec.url}")
    private long idTemplateEmailConfirmationDistanceAvecUrl;
    @Value("${mailjet.id.template.email.rappel.presentiel}")
    private long idTemplateEmailRappelPresentiel;
    @Value("${mailjet.id.template.email.rappel.distance}")
    private long idTemplateEmailRappelDistance;
    @Value("${mailjet.id.template.email.annulation}")
    private long idTemplateEmailAnnulation;

    public void envoyerMails(List<Personne> destinataires, long templateId, Map<String, String> variables, String subject) {

        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        Personne exp = new Personne("pe.actions@pole-emploi.fr", "Pôle emploi Action's");
        message.setFrom(exp);
        message.setSubject(subject);
        message.setTemplateID(templateId);
        message.setTo(destinataires);
        message.setVariables(variables);
        messages.add(message);
        try {
            this.envoyer(messages);
        } catch (IOException e) {
            logger.error("Erreur lors de l'envoie du mail", e);
        }

    }

    private void envoyer(List<Message> messages) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
	        final String encoded = Base64.getEncoder().encodeToString((mailjetApiKey + ':' + mailjetApiSecretKey).getBytes(StandardCharsets.UTF_8));
	        Response response = client.preparePost("https://api.mailjet.com/v3.1/send")
	                .setHeader("Content-Type", "application/json")
	                .addHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoded)
	                .setBody(mapper.writeValueAsString(new Email(messages)))
	                .execute()
	                .toCompletableFuture()
	                .join();
        } catch (IOException e) {
        	throw e;
        }
        finally {
        	client.close();
        }
    }


    public void envoieMailConfirmation(CandidatEvenement candidatEvenement) {
        Evenement evenement = candidatEvenement.getEvenement();
        Candidat candidat = candidatEvenement.getCandidat();
        long templateID;
        String subject = "Inscription confirmée : ".concat(evenement.getTitre());
        subject = this.addPrefixeToSubject(subject);
        Long typeID = evenement.getTypeId();
        String titre = evenement.getTitre();
        String date = evenement.getDateEvenement().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        String hour = evenement.getHeureDebut().toString().concat("-").concat(evenement.getHeureFin().toString());
        String eventURL = siteCandidatUrl.concat("/evenement/").concat(String.valueOf(evenement.getId()));
        String eventURLAnnulation = siteCandidatUrl.concat("/annulation/").concat(String.valueOf(evenement.getId()));
        String adresse = evenement.getAdresse()!=null?evenement.getAdresse():"";
        String cp = evenement.getCodePostal()!=null?evenement.getCodePostal():"";
        String ville = evenement.getVille()!=null?evenement.getVille():"";
        ModaliteAcces modaliteAcces = candidatEvenement.getModaliteAcces();
        Map<String, String> variables = new HashMap<>();
        if (modaliteAcces.getId().equals(EModaliteAcces.DISTANCE.getId())) {
            variables.put("webconfUrl", evenement.urlEnligne());
            templateID = evenement.urlEnligne() != null && !evenement.urlEnligne().isEmpty() ? this.idTemplateEmailConfirmationDistanceAvecUrl : this.idTemplateEmailConfirmationDistanceSansUrl;
        } else {
            variables.put("lieu", adresse.concat(" ").concat(cp.concat(" ").concat(ville)));
            templateID = this.idTemplateEmailConfirmation;
        }
        updateVariables(typeID, titre, date, hour, eventURL, eventURLAnnulation, variables);
        variables.put("typeEvenement", evenement.getType());

        getDestinataires(candidat, templateID, subject, variables);
    }

    public void envoieMailRappelVeille(CandidatEvenement candidatEvenement) {
        Evenement evenement = candidatEvenement.getEvenement();
        Candidat candidat = candidatEvenement.getCandidat();
        long templateID;
        String subject = "N’oubliez pas demain:".concat(evenement.getTitre());

        subject = this.addPrefixeToSubject(subject);
        Long typeID = evenement.getTypeId();
        String titre = evenement.getTitre();
        String date = evenement.getDateEvenement().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        String hour = evenement.getHeureDebut().toString().concat("-").concat(evenement.getHeureFin().toString());
        String eventURL = siteCandidatUrl.concat("/evenement/").concat(String.valueOf(evenement.getId()));
        String eventURLAnnulation = siteCandidatUrl.concat("/annulation/").concat(String.valueOf(evenement.getId()));
        ModaliteAcces modaliteAcces = candidatEvenement.getModaliteAcces();
        Map<String, String> variables = new HashMap<>();
        if (modaliteAcces.getId().equals(EModaliteAcces.DISTANCE.getId())) {
            templateID = this.idTemplateEmailRappelDistance;
            variables.put("webconfUrl", evenement.urlEnligne());
        } else {
            templateID = this.idTemplateEmailRappelPresentiel;
            variables.put("lieu", evenement.getAdresse().concat(" ").concat(evenement.getCodePostal().concat(" ").concat(evenement.getVille())));
        }
        updateVariables(typeID, titre, date, hour, eventURL, eventURLAnnulation, variables);
        variables.put("typeEvenement", evenement.getType());

        getDestinataires(candidat, templateID, subject, variables);
    }

    public void envoieMailAnnulation(CandidatEvenement candidatEvenement) {
        Evenement evenement = candidatEvenement.getEvenement();
        Candidat candidat = candidatEvenement.getCandidat();
        long templateID;
        String subject = "Annulation confirmée : ".concat(evenement.getTitre());
        subject = this.addPrefixeToSubject(subject);
        Long typeID = evenement.getTypeId();
        String titre = evenement.getTitre();
        String date = evenement.getDateEvenement().format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH));
        String hour = evenement.getHeureDebut().toString().concat("-").concat(evenement.getHeureFin().toString());
        String eventURL = siteCandidatUrl.concat("/evenement/").concat(String.valueOf(evenement.getId()));
        String eventURLAnnulation = siteCandidatUrl.concat("/annulation/").concat(String.valueOf(evenement.getId()));
        ModaliteAcces modaliteAcces = candidatEvenement.getModaliteAcces();
        Map<String, String> variables = new HashMap<>();
        if (modaliteAcces.getId().equals(EModaliteAcces.DISTANCE.getId())) {
            templateID = this.idTemplateEmailAnnulation;
            variables.put("webconfUrl", evenement.urlEnligne());
        } else {
            templateID = this.idTemplateEmailAnnulation;
            variables.put("lieu", evenement.getAdresse().concat(" ").concat(evenement.getCodePostal().concat(" ").concat(evenement.getVille())));
        }
        updateVariables(typeID, titre, date, hour, eventURL, eventURLAnnulation, variables);
        variables.put("typeEvenement", evenement.getType());

        getDestinataires(candidat, templateID, subject, variables);
    }

    private void getDestinataires(Candidat candidat, long templateID, String subject, Map<String, String> variables) {
        List<Personne> destinataires = new ArrayList<>();
        if (environnementProduction != null && environnementProduction) {
            destinataires.add(new Personne(candidat.getEmail(), candidat.getNom().concat(" ").concat(candidat.getPrenom())));
        } else {
            List<String> mailsToredirect = Arrays.asList(mailToRedirect.split(";"));
            mailsToredirect.stream().forEach(mail -> {
                destinataires.add(new Personne(mail, candidat.getNom().concat(" ").concat(candidat.getPrenom())));
            });
        }
        if (destinataires != null && !destinataires.isEmpty()) {
            this.envoyerMails(destinataires, templateID, variables, subject);
        }
    }

    private void updateVariables(Long typeID, String titre, String date, String hour, String eventURL, String eventURLAnnulation, Map<String, String> variables) {
        String article = null;
        List<Long> articlesLAIds = Arrays.asList(13l, 15L);
        List<Long> articlesLEIds = Arrays.asList(17L, 18l, 14l);
        List<Long> articlesLIds = Arrays.asList(16L);
        if (articlesLIds.contains(typeID)) {
            article = "L'";
        } else if (articlesLAIds.contains(typeID)) {
            article = "La";
        } else if (articlesLEIds.contains(typeID)) {
            article = "Le";
        }

        variables.put("article", article);
        variables.put("date", date);
        variables.put("titre", titre);
        variables.put("hour", hour);
        variables.put("eventURL", eventURL);
        variables.put("eventURLAnnulation", eventURLAnnulation);
    }

    private String addPrefixeToSubject(String subject) {
        if (environnementProduction == null || !environnementProduction) {
            return "ATTENTION TEST ".concat(subject);
        }
        return subject;
    }


}
