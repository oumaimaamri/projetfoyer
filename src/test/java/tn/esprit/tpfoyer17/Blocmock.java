package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.BlocService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class Blocmock{

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocService blocService;

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        bloc = Bloc.builder()
                .nomBloc("Bloc Test")
                .capaciteBloc(50L)
                .build();
    }

    @Test
    void testAddBloc() {
        // Mock du comportement du repository
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Appel de la méthode de service
        Bloc savedBloc = blocService.addBloc(bloc);

        // Vérifications
        assertNotNull(savedBloc, "Le bloc sauvegardé ne doit pas être nul.");
        assertEquals("Bloc Test", savedBloc.getNomBloc(), "Le nom du bloc doit correspondre.");
        assertEquals(50L, savedBloc.getCapaciteBloc(), "La capacité doit correspondre.");

        // Vérification des appels au mock
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testGetBlocById() {
        // Mock du comportement du repository
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Appel de la méthode de service
        Bloc foundBloc = blocService.getBlocById(1L);

        // Vérifications
        assertNotNull(foundBloc, "Le bloc trouvé ne doit pas être nul.");
        assertEquals("Bloc Test", foundBloc.getNomBloc(), "Le nom doit correspondre.");
        assertEquals(50L, foundBloc.getCapaciteBloc(), "La capacité doit correspondre.");

        // Vérification des appels au mock
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBloc() {
        // Préparer les données de mise à jour
        bloc.setNomBloc("Bloc Updated");
        bloc.setCapaciteBloc(100L);

        // Mock du comportement du repository
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Appel de la méthode de service
        Bloc updatedBloc = blocService.updateBloc(bloc);

        // Vérifications
        assertEquals("Bloc Updated", updatedBloc.getNomBloc(), "Le nom mis à jour doit correspondre.");
        assertEquals(100L, updatedBloc.getCapaciteBloc(), "La capacité mise à jour doit correspondre.");

        // Vérification des appels au mock
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testDeleteBloc() {
        // Appel de la méthode de service
        blocService.deleteBloc(1L);

        // Vérification que la méthode du repository a été appelée
        verify(blocRepository, times(1)).deleteById(1L);
    }
}
