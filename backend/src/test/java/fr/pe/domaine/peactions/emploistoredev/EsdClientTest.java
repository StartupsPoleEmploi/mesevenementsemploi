package fr.pe.domaine.peactions.emploistoredev;

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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import fr.pe.domaine.peactions.emploistoredev.ressources.DateNaissanceESD;

@ExtendWith(MockitoExtension.class)
public class EsdClientTest {
	
	@Mock
	private RestTemplateBuilder templateBuilder;
	
	@InjectMocks
	private EsdClient esdClient;
	
	@BeforeEach
	public void setUp() {
		RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
	    Mockito.when(templateBuilder.build()).thenReturn(restTemplate);
		
		ResponseEntity<DateNaissanceESD> dateNaissResponse = new ResponseEntity<DateNaissanceESD>(generateDateNaissance(), HttpStatus.OK);
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.eq(DateNaissanceESD.class)))
			.thenReturn(dateNaissResponse)
			.thenThrow(RestClientException.class);
	}
	
	@Test
	public void testCallDateNaissanceEndPoint() {
		//Check OK case
		DateNaissanceESD result = esdClient.callDateNaissanceEndPoint("anyBearerToken");
		assertNotNull(result);
		assertNotNull(result.getDateDeNaissance());
		assertTrue("Testington".contentEquals(result.getNomPatronymique()));
		//Check KO case
		result = esdClient.callDateNaissanceEndPoint("anyBearerToken");
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
