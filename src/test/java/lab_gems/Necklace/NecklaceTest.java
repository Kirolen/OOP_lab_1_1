package lab_gems.Necklace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
            when(inputReader.readNonNegativeInt("Enter ID of necklace to update:")).thenReturn(1);
            when(inputReader.readString("Enter new name (" + necklace.getName() + "):")).thenReturn("New Name");

            NecklaceOptions.updateNecklace();

            verify(necklaceService).updateNecklace(necklace);
            assertEquals("New Name", necklace.getName());
        }

        @Test
        void testDeleteNecklaceConfirmed() {
            Necklace necklace = new Necklace("DeleteMe");
            when(inputReader.readNonNegativeInt("Enter ID of necklace to delete:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);
            when(inputReader.readString("Are you sure you want to delete this necklace? (yes/no):")).thenReturn("yes");

            NecklaceOptions.deleteNecklace();

            verify(necklaceService).deleteNecklace(necklace);
        }

        @Test
        void testDeleteNecklaceCanceled() {
            Necklace necklace = new Necklace("KeepMe");
            when(inputReader.readNonNegativeInt("Enter ID of necklace to delete:")).thenReturn(2);
            when(necklaceService.getNecklaceById(2)).thenReturn(necklace);
            when(inputReader.readString("Are you sure you want to delete this necklace? (yes/no):")).thenReturn("no");

            NecklaceOptions.deleteNecklace();

            verify(necklaceService, never()).deleteNecklace(any());
        }

        @Test
        void testAddGemToNecklace_Success() {
            Necklace necklace = new Necklace("MyNecklace");
            necklace.setId(1);
            necklace.setGems(new ArrayList<>()); // пусто, отже каменя ще нема

            Gem gem = new Gem("Ruby", GemType.Precious, 1.5, 100.0, 0.8, "Red");
            gem.setId(2);

            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            when(inputReader.readNonNegativeInt("Enter Gem ID:")).thenReturn(2);
            when(gemService.getGemById(2)).thenReturn(gem);

            when(inputReader.readNonNegativeInt("Enter quantity:")).thenReturn(3);

            doNothing().when(necklaceService).addGemToNecklace(any(NecklaceGem.class));

            NecklaceOptions.addGemToNecklace();

            verify(necklaceService).addGemToNecklace(any(NecklaceGem.class));
        }

        @Test
        void testAddGemToNecklace_GemAlreadyExists() {
            Necklace necklace = new Necklace("MyNecklace");
            necklace.setId(1);

            Gem gem = new Gem("Ruby", GemType.Precious, 1.5, 100.0, 0.8, "Red");
            gem.setId(2);

            NecklaceGem existing = new NecklaceGem(necklace, gem, 1);
            List<NecklaceGem> gems = new ArrayList<>();
            gems.add(existing);
            necklace.setGems(gems);

            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            when(inputReader.readNonNegativeInt("Enter Gem ID:")).thenReturn(2);
            when(gemService.getGemById(2)).thenReturn(gem);

            NecklaceOptions.addGemToNecklace();

            verify(necklaceService, never()).addGemToNecklace(any(NecklaceGem.class));
        }

        @Test
        void testRemoveGemFromNecklace() {
            Necklace necklace = new Necklace("MyNecklace");
            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            when(inputReader.readNonNegativeInt("Enter Gem ID to remove:")).thenReturn(2);

            NecklaceOptions.removeGemFromNecklace();

            verify(necklaceService).removeGemFromNecklace(1, 2);
        }

        @Test
        void testSelectGemsForNecklace_FoundAndAddGem() {
            Gem gem = new Gem("Ruby", GemType.Precious, 1.5, 120.0, 0.8, "Red");
            List<Gem> gems = List.of(gem);

            when(inputReader.readIntBetweenXAndY("Choose gem precious type (1 - Precious, 2 - SemiPrecious):", 1, 2))
                    .thenReturn(1);

            when(inputReader.readNonNegativeDouble("Enter minimum weight (carats):")).thenReturn(1.0);
            when(inputReader.readNonNegativeDouble("Enter maximum weight (carats):")).thenReturn(2.0);

            when(inputReader.readNonNegativeDouble("Enter minimum price per carat:")).thenReturn(100.0);
            when(inputReader.readNonNegativeDouble("Enter maximum price per carat:")).thenReturn(200.0);

            when(inputReader.readMinAndMaxTransparency()).thenReturn(new double[] { 0.5, 0.9 });

            when(gemService.getGemsByCriteria(
                    eq(GemType.Precious),
                    eq(1.0), eq(2.0),
                    eq(100.0), eq(200.0),
                    eq(0.5), eq(0.9))).thenReturn(gems);

            when(inputReader.readString("Do you want to add a gem to the necklace? (yes/no):"))
                    .thenReturn("yes", "no");

            try (MockedStatic<NecklaceOptions> mockedStatic = mockStatic(NecklaceOptions.class, CALLS_REAL_METHODS)) {
                mockedStatic.when(NecklaceOptions::addGemToNecklace).thenAnswer(invocation -> null);

                NecklaceOptions.selectGemsForNecklace();

                verify(gemService).getGemsByCriteria(
                        eq(GemType.Precious),
                        eq(1.0), eq(2.0),
                        eq(100.0), eq(200.0),
                        eq(0.5), eq(0.9));

                mockedStatic.verify(NecklaceOptions::addGemToNecklace, times(1));
            }
        }

        @Test
        void testCalculateAndShowTotals_WithGems() {
            Necklace necklace = new Necklace("MyNecklace");
            Gem gem1 = new Gem("Ruby", GemType.Precious, 1.5, 100.0, 0.8, "Red");
            Gem gem2 = new Gem("Sapphire", GemType.Precious, 2.0, 150.0, 0.9, "Blue");

            List<NecklaceGem> gems = List.of(
                    new NecklaceGem(necklace, gem1, 2),
                    new NecklaceGem(necklace, gem2, 1));
            necklace.setGems(gems);

            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            double[] totals = NecklaceOptions.calculateAndShowTotals();

            assertEquals(5.0, totals[0], 0.001);
            assertEquals(600.0, totals[1], 0.001);
        }

        @Test
        void testCalculateAndShowTotals_EmptyNecklace() {
            Necklace necklace = new Necklace("EmptyNecklace");
            necklace.setGems(new ArrayList<>());

            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(2);
            when(necklaceService.getNecklaceById(2)).thenReturn(necklace);

            double[] totals = NecklaceOptions.calculateAndShowTotals();

            assertEquals(0.0, totals[0], 0.001);
            assertEquals(0.0, totals[1], 0.001);
        }

        @Test
        void testCalculateAndShowTotals_NecklaceNotFound() {
            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(3);
            when(necklaceService.getNecklaceById(3)).thenReturn(null);

            double[] totals = NecklaceOptions.calculateAndShowTotals();

            assertEquals(0.0, totals[0], 0.001);
            assertEquals(0.0, totals[1], 0.001);
        }

        @Test
        void testSortAndShowGemsByValue_WithGems() {
            Necklace necklace = new Necklace("MyNecklace");
            Gem gem1 = new Gem("Ruby", GemType.Precious, 1.5, 100.0, 0.8, "Red"); // value: 150
            Gem gem2 = new Gem("Sapphire", GemType.Precious, 2.0, 120.0, 0.9, "Blue"); // value: 240
            Gem gem3 = new Gem("Emerald", GemType.Precious, 1.0, 200.0, 0.9, "Green"); // value: 200

            List<NecklaceGem> gems = new ArrayList<>();
            gems.add(new NecklaceGem(necklace, gem1, 1));
            gems.add(new NecklaceGem(necklace, gem2, 1));
            gems.add(new NecklaceGem(necklace, gem3, 1));
            necklace.setGems(gems);

            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(1);
            when(necklaceService.getNecklaceById(1)).thenReturn(necklace);

            NecklaceOptions.sortAndShowGemsByValue();

            List<NecklaceGem> sorted = necklace.getGems();
            assertEquals("Sapphire", sorted.get(0).getGem().getName()); 
            assertEquals("Emerald", sorted.get(1).getGem().getName()); 
            assertEquals("Ruby", sorted.get(2).getGem().getName()); 
        }

        @Test
        void testSortAndShowGemsByValue_EmptyNecklace() {
            Necklace necklace = new Necklace("EmptyNecklace");
            necklace.setGems(new ArrayList<>());

            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(2);
            when(necklaceService.getNecklaceById(2)).thenReturn(necklace);

            NecklaceOptions.sortAndShowGemsByValue();

            assertTrue(necklace.getGems().isEmpty());
        }

        @Test
        void testSortAndShowGemsByValue_NecklaceNotFound() {
            when(inputReader.readNonNegativeInt("Enter Necklace ID:")).thenReturn(3);
            when(necklaceService.getNecklaceById(3)).thenReturn(null);

            NecklaceOptions.sortAndShowGemsByValue();
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
