package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.EtudiantService;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EtudiantMock {

    @InjectMocks
    private EtudiantService etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    public EtudiantMock() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testAddEtudiant() {
        // Création d'un étudiant fictif
        Etudiant etudiant = Etudiant.builder()
                .nomEtudiant("Doe")
                .prenomEtudiant("John")
                .cinEtudiant(12345678L)
                .dateNaissance(new Date())
                .build();

        // Simuler le comportement du repository
        when(etudiantRepository.save(any(Etudiant.class))).thenAnswer(invocation -> {
            Etudiant savedEtudiant = invocation.getArgument(0);
            savedEtudiant.setIdEtudiant(1L); // Simuler la génération d'un ID
            return savedEtudiant;
        });

        // Appeler la méthode du service
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Vérifications
        assertNotNull(savedEtudiant, "L'étudiant ne doit pas être nul après l'enregistrement");
        assertNotNull(savedEtudiant.getIdEtudiant(), "L'ID de l'étudiant ne doit pas être nul");
        assertEquals("Doe", savedEtudiant.getNomEtudiant(), "Le nom de l'étudiant est incorrect");
        assertEquals("John", savedEtudiant.getPrenomEtudiant(), "Le prénom de l'étudiant est incorrect");
        assertEquals(12345678L, savedEtudiant.getCinEtudiant(), "Le CIN de l'étudiant est incorrect");

        // Vérifier que le repository a été appelé exactement une fois
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    @Order(2)
    public void testUpdateEtudiant() {
        // Création d'un étudiant fictif existant
        Etudiant existingEtudiant = Etudiant.builder()
                .idEtudiant(2L)
                .nomEtudiant("Doe")
                .prenomEtudiant("John")
                .cinEtudiant(12345678L)
                .dateNaissance(new Date())
                .build();

        // Création de l'étudiant mis à jour
        Etudiant updatedEtudiant = Etudiant.builder()
                .idEtudiant(2L)
                .nomEtudiant("Smith")
                .prenomEtudiant("John")
                .cinEtudiant(87654321L)
                .dateNaissance(new Date())
                .build();

        // Simulation de la récupération de l'étudiant existant
        when(etudiantRepository.findById(2L)).thenReturn(Optional.of(existingEtudiant));

        // Simulation de la mise à jour de l'étudiant
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(updatedEtudiant);

        // Appeler la méthode de service pour mettre à jour l'étudiant
        Etudiant result = etudiantService.updateEtudiant(updatedEtudiant);

        // Vérifications
        assertNotNull(result, "L'étudiant mis à jour ne doit pas être nul");
        assertEquals(2L, result.getIdEtudiant(), "L'ID de l'étudiant est incorrect");
        assertEquals("Smith", result.getNomEtudiant(), "Le nom de l'étudiant est incorrect");
        assertEquals("John", result.getPrenomEtudiant(), "Le prénom de l'étudiant est incorrect");
        assertEquals(87654321L, result.getCinEtudiant(), "Le CIN de l'étudiant est incorrect");

        // Vérifier que la méthode save a été appelée exactement une fois
        verify(etudiantRepository, times(1)).save(updatedEtudiant);
    }


    @Test
    @Order(3)
    public void testDeleteEtudiant() {
        // ID d'étudiant fictif à supprimer
        Long etudiantId = 22L;

        // Appeler la méthode de service pour supprimer l'étudiant
        etudiantService.deleteEtudiant(etudiantId);

        // Vérifier que la méthode deleteById a été appelée exactement une fois
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }


}
