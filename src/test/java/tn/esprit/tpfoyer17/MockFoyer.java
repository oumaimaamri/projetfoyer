package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.services.IFoyerService;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TestFoyerServiceImplMock {

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private TestFoyerServiceImplMock foyerServiceImplTestMock;

    @Test
    @Order(1)
    void testAddFoyer() {
        // Créer un nouveau foyer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Central")
                .capaciteFoyer(100L)
                .build();

        log.info("Création d'un foyer : Nom = {}, Capacité = {}", foyer.getNomFoyer(), foyer.getCapaciteFoyer());

        // Simuler le comportement du service avec Mockito
        Foyer savedFoyer = Foyer.builder()
                .idFoyer(1L)
                .nomFoyer("Foyer Central")
                .capaciteFoyer(100L)
                .build();

        when(foyerService.addFoyer(foyer)).thenReturn(savedFoyer);

        // Appeler la méthode de service et vérifier le résultat
        log.info("Appel de la méthode foyerService.addFoyer avec le foyer simulé");
        Foyer result = foyerService.addFoyer(foyer);

        // Assertions pour vérifier que le foyer est correctement sauvegardé
        log.info("Vérification des assertions pour le foyer sauvegardé");
        Assertions.assertTrue(result.getIdFoyer() >0, "L'ID du foyer ne doit pas être nul");
        Assertions.assertEquals("Foyer Central", result.getNomFoyer());
        Assertions.assertEquals(100L, result.getCapaciteFoyer());

        // Vérifier que la méthode addFoyer a été appelée une fois avec le foyer fourni
        verify(foyerService, times(1)).addFoyer(foyer);
        log.info("Méthode foyerService.addFoyer vérifiée avec succès");
    }
}
