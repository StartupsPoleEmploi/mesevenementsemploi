package fr.pe.domaine.peactions.repository;

import fr.pe.domaine.peactions.model.Etat;
import fr.pe.domaine.peactions.model.Evenement;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class EvenementRepositoryTest {

    @Autowired
    private EvenementRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    private void setUp() {

        List<Evenement> events = new ArrayList<Evenement>();

        Evenement publishedEvent2 = initPublishedEvent(22, LocalTime.now(), LocalTime.now());
        events.add(publishedEvent2);
        Evenement publishedEvent1 = initPublishedEvent(20, LocalTime.now(), LocalTime.now());
        events.add(publishedEvent1);

        Evenement doneEvent1 = initDoneEvent(04,20, LocalTime.now(), LocalTime.now());
        events.add(doneEvent1);
        Evenement doneEvent2 = initDoneEvent(03,25, LocalTime.now(), LocalTime.now());
        events.add(doneEvent2);

        entityManager.persist(publishedEvent1);
        entityManager.persist(publishedEvent2);

        entityManager.persist(doneEvent1);
        entityManager.persist(doneEvent2);

        entityManager.flush();

    }

    Evenement initPublishedEvent(int jour, LocalTime heureDebut, LocalTime heureFin) {
        Evenement evenement = new Evenement();
        evenement.setDateEvenement(LocalDate.of(2050, 11, jour));
        evenement.setHeureDebut(heureDebut);
        evenement.setHeureFin(heureFin);
        evenement.setEstApublier(true);
        Query query = entityManager.createQuery("SELECT e FROM Etat e");
        List<Etat> list = (List<Etat>) query.getResultList();
        evenement.setEtat(list.get(1));
        return evenement;
    }

    Evenement initDoneEvent(int mois, int jour, LocalTime heureDebut, LocalTime heureFin) {
        Evenement evenement = new Evenement();
        evenement.setDateEvenement(LocalDate.of(2020, mois, jour));
        evenement.setHeureDebut(heureDebut);
        evenement.setHeureFin(heureFin);
        evenement.setEstApublier(true);
        return evenement;
    }

    @Test
    public void testGetUpcomingEvents() {
        List<Evenement> listPublishedEvenement = repository.getAllByDateEvenementAfterNow();
        Assertions.assertNotNull(listPublishedEvenement);
        Assertions.assertEquals(2, listPublishedEvenement.size());
        Assertions.assertTrue(listPublishedEvenement.get(0).getDateEvenement().isBefore(listPublishedEvenement.get(1).getDateEvenement()));
    }

    @Test
    public void testGetDoneEvents() {
        List<Evenement> listDoneEvenement = repository.getEvenementByDateEvenementBeforeNow();
        Assertions.assertNotNull(listDoneEvenement);
        Assertions.assertEquals(2, listDoneEvenement.size());
        Assertions.assertTrue(listDoneEvenement.get(0).getDateEvenement().isAfter(listDoneEvenement.get(1).getDateEvenement()));
    }

}
