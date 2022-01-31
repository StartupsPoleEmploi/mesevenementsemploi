package fr.pe.domaine.peactions;

import fr.pe.domaine.peactions.service.EvenementService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class UnitSpringBootApplicationTests {

	private final static String URI = "/api/evenements";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EvenementService evenementService;

/*	@Test
	public void testGetEvenement() throws Exception {


		// arrange
		given(evenementService.getEvenementById(0L)).willReturn(new Evenement("titre de l'evt", "description de l'evt"));

		// Get Evenement
		mockMvc.perform(get("/peactions/v1/evenement/0")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("titre").value("titre de l'evt")).andExpect(jsonPath("description").value("description de l'evt"));

	}

	@Test
	void testGetAllEvenements() throws Exception {

		// given

		Evenement evenement = new Evenement();

		evenement.setId(1l);
		evenement.setDescription("our evenement");

		evenement.setTitre("Africa");

		List<Evenement> evenements = Arrays.asList(evenement);
		given(evenementService.getAllEvenements(0, 10, "id")).willReturn(evenements);

		// when + then

		mockMvc.perform(get(URI)).andExpect(status().isOk())
				.andExpect(content().json("[{'id':1,'description':'our evenement', 'titre':'Africa'}]"));

	}*/

}
