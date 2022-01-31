package fr.pe.domaine.peactions.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.pe.domaine.peactions.emploistoredev.EsdClient;
import fr.pe.domaine.peactions.emploistoredev.ressources.DateNaissanceESD;

@ExtendWith(MockitoExtension.class)
public class IndividuServiceTest {
	
	@Mock
    private EsdClient esdClient;
	
	@InjectMocks
	private IndividuService individuService;
	
	@Test
	public void testGetDateNaissance() {
		//when
		Mockito.when(esdClient.callDateNaissanceEndPoint(Mockito.anyString()))
			.thenReturn(generateDateNaissance())
			.thenReturn(null);
		//then
		//Check OK case
		DateNaissanceESD result  = individuService.getDateNaissance("anyBearerToken");
		assertNotNull(result);
		assertNotNull(result.getDateDeNaissance());
		assertTrue("Testington".contentEquals(result.getNomPatronymique()));
		//Check KO (null) case
		result  = individuService.getDateNaissance("anyBearerToken");
		assertTrue(result == null);
	}
	
	private DateNaissanceESD generateDateNaissance() {
		DateNaissanceESD dateNaissance = new DateNaissanceESD();
		dateNaissance.setCodeCivilite("M");
		dateNaissance.setLibelleCivilite("Monsieur");
		dateNaissance.setNomPatronymique("Testington");
		dateNaissance.setPrenom("Lord");
		dateNaissance.setDateDeNaissance(new Date());
		return dateNaissance;
	}
}
