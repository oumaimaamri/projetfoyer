package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.FoyerService;
import java.util.NoSuchElementException;


@SpringBootTest
@Slf4j
public class FoyerTest {

    @Autowired
    private FoyerService foyerService;

    @Test
    @Order(1)
    public void testAddFoyer() {
        // Créer un nouveau foyer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Central 1")
                .capaciteFoyer(100L)
                .build();

        // Ajouter le foyer via le service
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Vérifier que l'ID n'est pas nul après la sauvegarde
        Assertions.assertNotNull(savedFoyer.getIdFoyer(), "L'ID du foyer ne doit pas être nul");
        Assertions.assertEquals("Foyer Central 1", savedFoyer.getNomFoyer());
        Assertions.assertEquals(100L, savedFoyer.getCapaciteFoyer());
    }



    @Test
    @Order(2)
    public void testUpdateFoyer() {
        // Récupérer un foyer existant (prérequis : un foyer doit être présent dans la base de données)
        Foyer foyer = foyerService.getFoyerById(2L);  // Remplacer par l'ID d'un foyer valide

        // Modifier certaines propriétés du foyer
        foyer.setNomFoyer("Foyer Mis à Jour");
        foyer.setCapaciteFoyer(300L);

        // Mettre à jour le foyer via le service
        Foyer updatedFoyer = foyerService.updateFoyer(foyer);

        // Vérifier que les modifications ont bien été appliquées
        Assertions.assertEquals("Foyer Mis à Jour", updatedFoyer.getNomFoyer());
        Assertions.assertEquals(300L, updatedFoyer.getCapaciteFoyer());
    }

    @Test
    @Order(3)
    public void testDeleteFoyer() {
        // Créer un foyer à supprimer
        Foyer foyer = Foyer.builder()
                .nomFoyer("Foyer Mis à Jour")
                .capaciteFoyer(300L)
                .build();

        // Ajouter le foyer via le service
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Vérifier que le foyer a été ajouté
        Assertions.assertNotNull(savedFoyer.getIdFoyer(), "L'ID du foyer ne doit pas être nul");

        // Supprimer le foyer
        foyerService.deleteFoyer(savedFoyer.getIdFoyer());

        // Vérifier que le foyer a bien été supprimé
        Assertions.assertThrows(NoSuchElementException.class,
                () -> foyerService.getFoyerById(savedFoyer.getIdFoyer()),
                "Une exception doit être levée car le foyer est supprimé");

    }
}
