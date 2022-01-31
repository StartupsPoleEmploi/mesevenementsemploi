package fr.pe.domaine.peactions;

import fr.pe.domaine.peactions.exception.ResourceNotFoundException;
import fr.pe.domaine.peactions.model.Evenement;
import fr.pe.domaine.peactions.repository.EvenementRepository;
import fr.pe.domaine.peactions.service.EvenementService;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EvenementServiceTest {

  private AutoCloseable closeable;

  @Mock
  private EvenementRepository repository;

  @InjectMocks
  private EvenementService evenementService;

  @BeforeEach
  void initService() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void closeService() throws Exception {
    closeable.close();
  }

  @Test
  @DisplayName("Test de récupération des offres par id  OK")
  public void testGetEvenementById_ok() {

    //given
    Evenement evenementAttendu = new Evenement();
    evenementAttendu.setId(1L);

    //mock du repository
    when(repository.findById(1L)).thenReturn(Optional.of(evenementAttendu));

    //when
    Evenement evenementResultat = evenementService.getEvenementById(1L);

    //test
    Assertions.assertEquals(evenementAttendu.getId(), evenementResultat.getId(), "L'évenement retourné n'est pas le bon");

  }

  @Test(expected = ResourceNotFoundException.class)
  @DisplayName("Test de récupération des offres par id KO")
    public void testGetEvenementById_ko() {

    //given
    Evenement evenementAttendu = new Evenement();
    evenementAttendu.setId(2L);

    //test
    evenementService.getEvenementById(2L);

  }

  @Test
  @DisplayName("Test de récupération des évènements ordonnés par status")
  public void testGetBackEventsListOrderByStatus_ok() {

    //given
    List<Evenement> inProgressEvent = new ArrayList<>();
    List<Evenement> doneEvents = new ArrayList<>();

    inProgressEvent.add(new Evenement());
    doneEvents.add(new Evenement());

    //mock du repository
    when(repository.getAllByDateEvenementAfterNow()).thenReturn(inProgressEvent);
    when(repository.getEvenementByDateEvenementBeforeNow()).thenReturn(doneEvents);

    //when
    List<Evenement> result = evenementService.getBackEventsListOrderByStatus();

    //test
    assertNotNull(result);
    assertEquals(2, result.size());

  }

  @Test
  @DisplayName("Test de récupération des événements par agence pour le conseiller")
  public void testGetBackEventsListOrderByStatusAndByAgency() {

    String codeEtablissement = "31096";
    //given
    List<Evenement> inProgressEvent = new ArrayList<>();
    List<Evenement> doneEvents = new ArrayList<>();

    inProgressEvent.add(new Evenement());
    doneEvents.add(new Evenement());

    //mock du repository
    when(repository.getByCodeEtablissementAllAndDateEvenementAfterNow(codeEtablissement)).thenReturn(inProgressEvent);
    when(repository.getByCodeEtablissementEvenementByDateEvenementBeforeNow(codeEtablissement)).thenReturn(doneEvents);

    //when
    List<Evenement> result = evenementService.getBackEventsListOrderByStatusAndByAgency(codeEtablissement);
    List<Evenement> emptyResult = evenementService.getBackEventsListOrderByStatusAndByAgency("5001");

    //test
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(0, emptyResult.size());
  }

  @Test
  @DisplayName("Test de récupération des événements paginés par agence")
  public void testGetBackEventsPagesOrderByStatusAndByAgency() {

    //given
    Integer page = 0;
    String codeEtablissement = "46001";
    List<Evenement> inProgressEvent = new ArrayList<>();
    List<Evenement> doneEvents = new ArrayList<>();

    inProgressEvent.add(new Evenement());
    doneEvents.add(new Evenement());

    //mock du repository
    when(repository.getByCodeEtablissementAllAndDateEvenementAfterNow(codeEtablissement)).thenReturn(inProgressEvent);
    when(repository.getByCodeEtablissementEvenementByDateEvenementBeforeNow(codeEtablissement)).thenReturn(doneEvents);

    //when
    Page<Evenement> result = evenementService.getBackEventsPagesOrderByStatusAndByAgency(codeEtablissement, page, 1);
    Page<Evenement> resultTwoPages = evenementService.getBackEventsPagesOrderByStatusAndByAgency(codeEtablissement, page, 2);

    //test
    assertNotNull(result);
    assertEquals(result.getTotalPages(), 2);
    assertEquals(result.getTotalElements(), 2);

    assertEquals(resultTwoPages.getTotalPages(), 1);
    assertEquals(resultTwoPages.getTotalElements(), 2);

  }

}
