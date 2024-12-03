import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.services.EtudiantService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class EtudiantTest {

    @Autowired
    private EtudiantService etudiantService;

    @Test
    @Order(1)
    public void testAddEtudiant() {
        log.info("Testing: Adding a new student");

        // Créer un nouvel étudiant
        Etudiant etudiant = Etudiant.builder()
                .nomEtudiant("Doe")
                .prenomEtudiant("John")
                .cinEtudiant(12345678L)
                .dateNaissance(new Date())
                .build();

        // Ajouter l'étudiant via le service
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Assertions pour valider les résultats
        assertNotNull(savedEtudiant, "L'étudiant ne doit pas être nul après l'enregistrement");
        assertNotNull(savedEtudiant.getIdEtudiant(), "L'ID de l'étudiant ne doit pas être nul");
        assertEquals("Doe", savedEtudiant.getNomEtudiant(), "Le nom de l'étudiant est incorrect");
        assertEquals("John", savedEtudiant.getPrenomEtudiant(), "Le prénom de l'étudiant est incorrect");
        assertEquals(12345678L, savedEtudiant.getCinEtudiant(), "Le CIN de l'étudiant est incorrect");

        log.info("Test terminé : L'étudiant a été ajouté avec succès");
    }
}