package fr.pe.domaine.peactions.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.pe.domaine.peactions.model.StatutInscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.pe.domaine.peactions.model.Candidat;
import fr.pe.domaine.peactions.model.CandidatEvenement;
import fr.pe.domaine.peactions.model.Evenement;

@DataJpaTest
public class CandidatEvenementRepositoryTest {
	
	@Autowired
	private CandidatEvenementRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@BeforeEach
	private void setUp() {


		//Event
		Evenement event = new Evenement("Test", "Ceci est un test");
		event.setTitre("Test");
		event.setDescription("Ceci est un test");
		entityManager.persist(event);
		//Candidat
		Candidat candidat = new Candidat();
		candidat.setPrenom("Lord");
		candidat.setNom("Testington");
		candidat.setIdentifiantCrypter("123456789");
		candidat.setCivilite("M");
		candidat.setDateNaissance(new Date());
		entityManager.persist(candidat);

		//StatutI
		StatutInscription statutInscription = new StatutInscription();
		statutInscription.setLibelle("Inscrit");
		entityManager.persist(statutInscription);

		entityManager.flush();
	}
	
	private Candidat getCandidat() {
		 Query query = entityManager.createQuery("SELECT c FROM Candidat c");
		List<Candidat> list = query.getResultList();
		 return list.get(0);
	}
	
	private Evenement getEvent() {
		 Query query = entityManager.createQuery("SELECT e FROM Evenement e");
		 List<Evenement> list = query.getResultList();
		return list.get(0);
	}

	private StatutInscription getStatutInscription() {
		 Query query = entityManager.createQuery("SELECT s FROM StatutInscription s ");

		List<StatutInscription> list =  query.getResultList();
		 return list.get(0);
	}



	@Test
	public void  testCreateCandidatEvenement() {
		// given
		Candidat candidat = getCandidat();
		Evenement event = getEvent();
		StatutInscription statutInscription = getStatutInscription();
		CandidatEvenement candidatEvenement = new CandidatEvenement(candidat, event);
		candidatEvenement.setStatutInscription(statutInscription);
		//when
		candidatEvenement = repository.save(candidatEvenement);
		//then
		assertNotNull(candidatEvenement);
		assertTrue(candidatEvenement.getId().getCandidatId() == candidat.getId());
		assertTrue(candidatEvenement.getId().getEvenementId() == event.getId());
		assertNotNull(candidatEvenement.getDateInscription()); // checks that PrePersist on CandidatEvenement was called
		assertTrue(candidatEvenement.getDateModification() == null); // checks that PreUpdate on CandidatEvenement was NOT called
		assertTrue(candidatEvenement.getDateDesinscription() == null);
		
    }



	@Test
	public void  testUpdateCandidatEvenement() throws ParseException {
		// given
		Candidat candidat = getCandidat();
		Evenement event = getEvent();
		StatutInscription statutInscription = getStatutInscription();
		CandidatEvenement oldInscription = new CandidatEvenement(candidat, event);
		oldInscription.setStatutInscription(statutInscription);
		entityManager.persist(oldInscription);
		entityManager.flush();
		CandidatEvenement newInscription = entityManager.find(CandidatEvenement.class, oldInscription.getId());
		newInscription.setStatutInscription(statutInscription);
		//when
		newInscription.setDateInscription(sdf.parse("31/12/2020")); //Change any value on the entity
		newInscription = repository.save(newInscription); // Execute an update query
		entityManager.flush();
		//then
		assertNotNull(newInscription);
		assertTrue(newInscription.getId().getCandidatId() == candidat.getId());
		assertTrue(newInscription.getId().getEvenementId() == event.getId());
		assertTrue("31/12/2020".equals(sdf.format(newInscription.getDateInscription()))); //checks that PrePersist on CandidatEvenement was NOT called
		assertNotNull(newInscription.getDateModification()); // checks that PreUpdate on CandidatEvenement was called
		assertTrue(newInscription.getDateDesinscription() == null);
    }


}
