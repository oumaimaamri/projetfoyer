package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlocTest {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private FoyerRepository foyerRepository;

    private Bloc bloc;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Create and save a Foyer
        foyer = new Foyer();
        foyer.setNomFoyer("Foyer Test");
        foyer.setCapaciteFoyer(200L);
        foyerRepository.save(foyer);

        // Create a Bloc with the Foyer
        bloc = new Bloc();
        bloc.setNomBloc("Bloc Test");
        bloc.setCapaciteBloc(100L);
        bloc.setFoyer(foyer);
    }

    @Test
    void testCreateBloc() {
        Bloc savedBloc = blocRepository.save(bloc);

        assertNotNull(savedBloc.getIdBloc());
        assertEquals("Bloc Test", savedBloc.getNomBloc());
        assertEquals(100L, savedBloc.getCapaciteBloc());
        assertEquals(foyer.getIdFoyer(), savedBloc.getFoyer().getIdFoyer());
    }

    @Test
    void testReadBloc() {
        Bloc savedBloc = blocRepository.save(bloc);
        Bloc foundBloc = blocRepository.findById(savedBloc.getIdBloc()).orElse(null);

        assertNotNull(foundBloc);
        assertEquals(savedBloc.getIdBloc(), foundBloc.getIdBloc());
        assertEquals("Bloc Test", foundBloc.getNomBloc());
    }

    @Test
    void testUpdateBloc() {
        Bloc savedBloc = blocRepository.save(bloc);
        savedBloc.setNomBloc("Updated Bloc");
        savedBloc.setCapaciteBloc(150L);

        Bloc updatedBloc = blocRepository.save(savedBloc);

        assertEquals("Updated Bloc", updatedBloc.getNomBloc());
        assertEquals(150L, updatedBloc.getCapaciteBloc());
    }

    @Test
    void testDeleteBloc() {
        Bloc savedBloc = blocRepository.save(bloc);
        Long blocId = savedBloc.getIdBloc();

        blocRepository.deleteById(blocId);

        Bloc deletedBloc = blocRepository.findById(blocId).orElse(null);

        assertNull(deletedBloc);
    }
}

