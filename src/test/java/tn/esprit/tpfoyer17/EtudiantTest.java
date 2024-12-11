package tn.esprit.tpfoyer17;
import org.junit.jupiter.api.RepeatedTest;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.EtudiantService;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Etudiant;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class EtudiantTest {

    @Autowired
    private EtudiantService etudiantService;
    @Autowired
    private EtudiantRepository etudiantRepository;


    @Test
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

    @Test

    public void testUpdateEtudiant() {
        log.info("Testing: Updating a student");

        // Ajouter un étudiant pour l'utiliser dans le test de mise à jour
        Etudiant etudiant = Etudiant.builder()
                .nomEtudiant("Original")
                .prenomEtudiant("Name")
                .cinEtudiant(98765432L)
                .dateNaissance(new Date())
                .build();
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Modifier les propriétés de l'étudiant
        savedEtudiant.setNomEtudiant("Updated Name");
        savedEtudiant.setPrenomEtudiant("Updated Surname");

        // Mettre à jour l'étudiant via le service
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(savedEtudiant);

        // Assertions pour valider les modifications
        assertNotNull(updatedEtudiant, "L'étudiant mis à jour ne doit pas être nul");
        assertEquals("Updated Name", updatedEtudiant.getNomEtudiant(), "Le nom de l'étudiant est incorrect après mise à jour");
        assertEquals("Updated Surname", updatedEtudiant.getPrenomEtudiant(), "Le prénom de l'étudiant est incorrect après mise à jour");

        log.info("Test terminé : L'étudiant a été mis à jour avec succès");
    }


    @Test
    public void testDeleteEtudiant() {
        // Ajouter un étudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Doe");
        etudiant.setPrenomEtudiant("John");
        etudiant.setCinEtudiant(12345678L);
        etudiant.setDateNaissance(new Date());

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        assertNotNull(savedEtudiant);

        // Supprimer l'étudiant
        etudiantRepository.deleteById(savedEtudiant.getIdEtudiant());

        // Vérifiez qu'il n'existe plus
        assertFalse(etudiantRepository.existsById(savedEtudiant.getIdEtudiant()));
    }





}





