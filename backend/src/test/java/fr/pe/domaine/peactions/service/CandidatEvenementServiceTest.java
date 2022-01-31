package fr.pe.domaine.peactions.service;

import fr.pe.domaine.peactions.model.Candidat;
import fr.pe.domaine.peactions.model.CandidatEvenement;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.model.StatutInscription;
import fr.pe.domaine.peactions.repository.CandidatEvenementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CandidatEvenementServiceTest {
	
	@Mock
	private CandidatEvenementRepository repository;
	
	@InjectMocks
	private CandidatEvenementService service;
	
	@BeforeEach
	public void setup() {
		Evenement event = new Evenement();
		Candidat candidat = new Candidat();
		CandidatEvenement candidatEvenement = new CandidatEvenement(candidat, event);
		candidatEvenement.setDateInscription(new Date());
		candidatEvenement.setDateModification(new Date());
		Mockito.when(repository.save(Mockito.any())).thenReturn(candidatEvenement);
	}
	
	@Test
	public void  testCreateCandidatEvenement() {
		// given
		Candidat candidat = new Candidat();
		candidat.setId(1L);
		Evenement event = new Evenement();
		event.setId(1L);
		CandidatEvenement candidatEvenement = new CandidatEvenement(candidat, event);
		//when
		candidatEvenement = service.createCandidatEvenement(candidatEvenement);
		//then
		assertNotNull(candidatEvenement);
		assertNotNull(candidatEvenement.getDateInscription());
    }
	
	@Test
	public void  testUpdateCandidatEvenement() {
		// given
		Candidat candidat = new Candidat();
		candidat.setId(1L);
		Evenement event = new Evenement();
		event.setId(1L);
		CandidatEvenement candidatEvenement = new CandidatEvenement(candidat, event);
		StatutInscription statut = new StatutInscription();
		statut.setId(1);
		candidatEvenement.setStatutInscription(statut);
		//when
		candidatEvenement = service.updateCandidatEvenement(candidatEvenement);
		//then
		assertNotNull(candidatEvenement);
		assertNotNull(candidatEvenement.getDateModification());
	}
	
	
	

}
