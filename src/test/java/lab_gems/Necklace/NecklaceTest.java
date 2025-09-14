package lab_gems.Necklace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lab_gems.model.*;
import lab_gems.services.GemService;
import lab_gems.services.NecklaceService;
import lab_gems.types.GemType;
import lab_gems.ui.InputReader;
import lab_gems.ui.ui_options.NecklaceOptions;

class NecklaceTest {

    @Mock
    private InputReader inputReader;

    @Mock
    private NecklaceService necklaceService;

    @Mock
    private GemService gemService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        NecklaceOptions.setInputReader(inputReader);
        NecklaceOptions.setNecklaceService(necklaceService);
        NecklaceOptions.setGemService(gemService);
    }

    @Nested
    class LogicNecklaceTest {

        @Test
        void testAddNecklace() {
            when(inputReader.readString("Enter necklace name:")).thenReturn("My Necklace");

            NecklaceOptions.addNecklace();

            verify(necklaceService).saveNecklace(any(Necklace.class));
        }

        @Test
        void testUpdateNecklace() {
            Necklace necklace = new Necklace("Old Name");
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);
            when(inputReader.readInt("Enter ID of necklace to update:")).thenReturn(1);
            when(inputReader.readString("Enter new name (" + necklace.getName() + "):")).thenReturn("New Name");

            NecklaceOptions.updateNecklace();

            verify(necklaceService).updateNecklace(necklace);
            assertEquals("New Name", necklace.getName());
        }

        @Test
        void testDeleteNecklaceConfirmed() {
            Necklace necklace = new Necklace("DeleteMe");
            when(inputReader.readInt("Enter ID of necklace to delete:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);
            when(inputReader.readString("Are you sure you want to delete this necklace? (yes/no):")).thenReturn("yes");

            NecklaceOptions.deleteNecklace();

            verify(necklaceService).deleteNecklace(necklace);
        }

        @Test
        void testDeleteNecklaceCanceled() {
            Necklace necklace = new Necklace("KeepMe");
            when(inputReader.readInt("Enter ID of necklace to delete:")).thenReturn(2);
            when(necklaceService.getNecklaceById(2)).thenReturn(necklace);
            when(inputReader.readString("Are you sure you want to delete this necklace? (yes/no):")).thenReturn("no");

            NecklaceOptions.deleteNecklace();

            verify(necklaceService, never()).deleteNecklace(any());
        }

        @Test
        void testAddGemToNecklace() {
            Necklace necklace = new Necklace("MyNecklace");
            Gem gem = new Gem("Ruby", GemType.Precious, 1.5, 100.0, 0.8, "Red");

            when(inputReader.readInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            when(inputReader.readInt("Enter Gem ID:")).thenReturn(2);
            when(gemService.getGemById(2)).thenReturn(gem);

            when(inputReader.readInt("Enter quantity:")).thenReturn(3);

            doNothing().when(necklaceService).addGemToNecklace(any(NecklaceGem.class));

            NecklaceOptions.addGemToNecklace();

            verify(necklaceService).addGemToNecklace(any(NecklaceGem.class));
        }

        @Test
        void testRemoveGemFromNecklace() {
            Necklace necklace = new Necklace("MyNecklace");
            when(inputReader.readInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            when(inputReader.readInt("Enter Gem ID to remove:")).thenReturn(2);

            NecklaceOptions.removeGemFromNecklace();

            verify(necklaceService).removeGemFromNecklace(1, 2);
        }
    }

    @Nested
    class NecklaceBaseTest {

        @Test
        void testNecklaceGettersSetters() {
            Necklace necklace = new Necklace();
            necklace.setName("Golden Necklace");
            necklace.setId(1);

            assertEquals("Golden Necklace", necklace.getName());
            assertEquals(1, necklace.getId());
        }

        @Test
        void testNecklaceGemGettersSetters() {
            Necklace necklace = new Necklace("Fancy");
            Gem gem = new Gem("Emerald", null, 2.0, 200.0, 0.9, "Green");
            NecklaceGem ng = new NecklaceGem(necklace, gem, 5);

            assertEquals(necklace, ng.getNecklace());
            assertEquals(gem, ng.getGem());
            assertEquals(5, ng.getQuantity());

            ng.setQuantity(10);
            assertEquals(10, ng.getQuantity());
        }

        @Test
        void testNecklaceWithMultipleGems() {
            Necklace necklace = new Necklace("Party Necklace");
            Gem gem1 = new Gem("Ruby", null, 1.0, 100.0, 0.9, "Red");
            Gem gem2 = new Gem("Sapphire", null, 2.0, 150.0, 0.8, "Blue");

            List<NecklaceGem> gems = new ArrayList<>();
            gems.add(new NecklaceGem(necklace, gem1, 2));
            gems.add(new NecklaceGem(necklace, gem2, 3));

            necklace.setGems(gems);

            assertEquals(2, necklace.getGems().size());
            assertEquals(gem1, necklace.getGems().get(0).getGem());
            assertEquals(gem2, necklace.getGems().get(1).getGem());
        }
    }
}
