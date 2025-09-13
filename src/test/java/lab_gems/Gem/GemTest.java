package lab_gems.Gem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lab_gems.model.*;
import lab_gems.services.GemService;
import lab_gems.types.GemType;
import lab_gems.types.OpalType;
import lab_gems.ui.InputReader;
import lab_gems.ui.ui_options.GemOptions;

class GemTest {

    @Mock
    private InputReader inputReader;

    @Mock
    private GemService gemService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        GemOptions.setInputReader(inputReader);
        GemOptions.setGemService(gemService);
    }

    @Nested
    class LogicGemTest {
        @Test
        void testAddAllGemTypes() {
            for (int gemType = 0; gemType <= 4; gemType++) {
                when(inputReader.readString("Enter gem name:")).thenReturn("TestGem_" + gemType);
                when(inputReader
                        .readInt("Choose gem type (0 - Default, 1 - Opal, 2 - Amethyst, 3 - Topaz, 4 - Sapphire):"))
                        .thenReturn(gemType);
                when(inputReader.readInt("Choose gem precious type (1 - Precious, 2 - SemiPrecious):")).thenReturn(1);
                when(inputReader.readDouble("Enter gem weight (carats):")).thenReturn(1.5);
                when(inputReader.readDouble("Enter price per carat:")).thenReturn(100.0);
                when(inputReader.readDouble("Enter transparency (0.0 - 1.0):")).thenReturn(0.8);
                when(inputReader.readString("Enter gem color:")).thenReturn("Red");

                switch (gemType) {
                    case 1: // Opal
                        when(inputReader
                                .readInt(
                                        "Choose Opal type (1 - White, 2 - Black, 3 - Fire, 4 - Boulder, 5 - Crystal):"))
                                .thenReturn(3);
                        when(inputReader.readDouble("Enter fire intensity:")).thenReturn(0.9);
                        break;
                    case 2: // Amethyst
                        when(inputReader.readDouble("Enter clarity (0.0 - 1.0):")).thenReturn(0.7);
                        when(inputReader.readDouble("Enter color intensity (0.0 - 1.0):")).thenReturn(0.6);
                        break;
                    case 3: // Topaz
                        when(inputReader.readDouble("Enter hardness:")).thenReturn(5.5);
                        when(inputReader.readDouble("Enter light reflectivity:")).thenReturn(0.8);
                        break;
                    case 4: // Sapphire
                        when(inputReader.readDouble("Enter hardness:")).thenReturn(7.5);
                        when(inputReader.readDouble("Enter brilliance:")).thenReturn(9.0);
                        break;
                }

                GemOptions.addGem();

                verify(gemService).saveGem(any(Gem.class));
                clearInvocations(gemService);
            }
        }

        @Test
        void testUpdateGemAllFields() {
            Gem gem = new Gem("Diamond", GemType.Precious, 2.0, 150.0, 0.9, "Clear");
            when(gemService.getGemById(1)).thenReturn(gem);

            when(inputReader.readInt("Enter ID of gem to update:")).thenReturn(1);
            when(inputReader.readString("Enter new name (" + gem.getName() + "):")).thenReturn("Ruby");
            when(inputReader
                    .readString("Choose gem precious type (1 - Precious, 2 - SemiPrecious) (" + gem.getType() + "):"))
                    .thenReturn("2");
            when(inputReader.readString("Enter new weight in carats (" + gem.getWeightCarat() + "):"))
                    .thenReturn("3.5");
            when(inputReader.readString("Enter new price per carat (" + gem.getPricePerCarat() + "):"))
                    .thenReturn("200");
            when(inputReader.readString("Enter new transparency (0.0-1.0) (" + gem.getTransparency() + "):"))
                    .thenReturn("0.8");
            when(inputReader.readString("Enter new color (" + gem.getColor() + "):")).thenReturn("Red");

            GemOptions.updateGem();

            verify(gemService).updateGem(gem);

            assertEquals("Ruby", gem.getName());
            assertEquals(GemType.SemiPrecious, gem.getType());
            assertEquals(3.5, gem.getWeightCarat());
            assertEquals(200.0, gem.getPricePerCarat());
            assertEquals(0.8, gem.getTransparency());
            assertEquals("Red", gem.getColor());
        }

        @Test
        void testUpdateGemLeaveFieldsUnchanged() {
            Gem gem = new Gem("Emerald", GemType.Precious, 1.5, 120.0, 0.9, "Green");
            when(gemService.getGemById(2)).thenReturn(gem);

            when(inputReader.readInt("Enter ID of gem to update:")).thenReturn(2);
            when(inputReader.readString(anyString())).thenReturn("");

            GemOptions.updateGem();

            verify(gemService).updateGem(gem);

            assertEquals("Emerald", gem.getName());
            assertEquals(GemType.Precious, gem.getType());
            assertEquals(1.5, gem.getWeightCarat());
            assertEquals(120.0, gem.getPricePerCarat());
            assertEquals(0.9, gem.getTransparency());
            assertEquals("Green", gem.getColor());
        }

        @Test
        void testUpdateGemNotFound() {
            when(gemService.getGemById(999)).thenReturn(null);
            when(inputReader.readInt("Enter ID of gem to update:")).thenReturn(999);

            GemOptions.updateGem();

            verify(gemService, never()).updateGem(any());
        }

        @Test
        void testDeleteGemConfirmed() {
            Gem gem = new Gem("Diamond", null, 2.0, 150.0, 0.9, "Clear");

            when(inputReader.readInt("Enter ID of gem to delete:")).thenReturn(1);
            when(gemService.getGemById(1)).thenReturn(gem);
            when(inputReader.readString("Are you sure you want to delete this gem? (yes/no):")).thenReturn("yes");

            GemOptions.deleteGem();

            verify(gemService).deleteGem(1);
        }

        @Test
        void testDeleteGemCanceled() {
            Gem gem = new Gem("Ruby", null, 1.5, 100.0, 0.8, "Red");

            when(inputReader.readInt("Enter ID of gem to delete:")).thenReturn(2);
            when(gemService.getGemById(2)).thenReturn(gem);
            when(inputReader.readString("Are you sure you want to delete this gem? (yes/no):")).thenReturn("no");

            GemOptions.deleteGem();

            verify(gemService, never()).deleteGem(anyInt());
        }

        @Test
        void testDeleteGemNotFound() {
            when(inputReader.readInt("Enter ID of gem to delete:")).thenReturn(3);
            when(gemService.getGemById(3)).thenReturn(null);

            GemOptions.deleteGem();

            verify(gemService, never()).deleteGem(anyInt());
        }
    }

    @Nested
    class GemBaseTest {
        @Test
        void testGemConstructorAndGetters() {
            Gem gem = new Gem("Diamond", GemType.Precious, 2.0, 150.0, 0.9, "Clear");
            assertEquals("Diamond", gem.getName());
            assertEquals(GemType.Precious, gem.getType());
            assertEquals(2.0, gem.getWeightCarat());
            assertEquals(150.0, gem.getPricePerCarat());
            assertEquals(0.9, gem.getTransparency());
            assertEquals("Clear", gem.getColor());
        }

        @Test
        void testSetters() {
            Gem gem = new Gem();
            gem.setName("Ruby");
            gem.setType(GemType.Precious);
            gem.setWeightCarat(1.5);
            gem.setPricePerCarat(200.0);
            gem.setTransparency(0.85);
            gem.setColor("Red");

            assertEquals("Ruby", gem.getName());
            assertEquals(GemType.Precious, gem.getType());
            assertEquals(1.5, gem.getWeightCarat());
            assertEquals(200.0, gem.getPricePerCarat());
            assertEquals(0.85, gem.getTransparency());
            assertEquals("Red", gem.getColor());
        }

        @Test
        void testTotalPrice() {
            Gem gem = new Gem("Emerald", GemType.Precious, 3.0, 100.0, 0.8, "Green");
            assertEquals(300.0, gem.getTotalPrice());
        }
    }

    @Nested
    class TopazTest {
        @Test
        void testTopazConstructorAndGetters() {
            Topaz topaz = new Topaz("Topaz", GemType.SemiPrecious, 2.5, 50.0,
                    0.7, "Yellow", 7.0, 0.8);
            assertEquals("Topaz", topaz.getName());
            assertEquals(GemType.SemiPrecious, topaz.getType());
            assertEquals(2.5, topaz.getWeightCarat());
            assertEquals(50.0, topaz.getPricePerCarat());
            assertEquals(0.7, topaz.getTransparency());
            assertEquals("Yellow", topaz.getColor());
            assertEquals(7.0, topaz.getHardness());
            assertEquals(0.8, topaz.getLightReflectivity());
            assertEquals(125.0, topaz.getTotalPrice());
        }

        @Test
        void testTopazSetters() {
            Topaz topaz = new Topaz();
            topaz.setName("Topaz");
            topaz.setWeightCarat(3.0);
            topaz.setPricePerCarat(60.0);
            topaz.setHardness(6.5);
            topaz.setLightReflectivity(0.75);

            assertEquals(180.0, topaz.getTotalPrice());
            assertEquals(6.5, topaz.getHardness());
        }
    }

    @Nested
    class SapphireTest {
        @Test
        void testSapphireConstructorAndGetters() {
            Sapphire sapphire = new Sapphire("Sapphire", GemType.Precious, 4.0, 160.0,
                    0.7, "Blue", 9.0, 85.0);
            assertEquals("Sapphire", sapphire.getName());
            assertEquals(GemType.Precious, sapphire.getType());
            assertEquals(4.0, sapphire.getWeightCarat());
            assertEquals(160.0, sapphire.getPricePerCarat());
            assertEquals(0.7, sapphire.getTransparency());
            assertEquals("Blue", sapphire.getColor());
            assertEquals(9.0, sapphire.getHardness());
            assertEquals(85.0, sapphire.getBrilliance());
            assertEquals(640.0, sapphire.getTotalPrice());
        }
    }

    @Nested
    class AmethystTest {
        @Test
        void testAmethystConstructorAndGetters() {
            Amethyst amethyst = new Amethyst("Amethyst", GemType.Precious, 1.5, 80.0,
                    0.9, "Purple", 0.85, 0.75);
            assertEquals("Amethyst", amethyst.getName());
            assertEquals(0.85, amethyst.getClarity());
            assertEquals(0.75, amethyst.getColorIntensity());
            assertEquals(120.0, amethyst.getTotalPrice());
        }
    }

    @Nested
    class OpalTest {
        @Test
        void testOpalConstructorAndGetters() {
            Opal opal = new Opal("Opal", GemType.SemiPrecious, 2.0, 70.0,
                    0.8, "White", OpalType.Fire, 0.95);
            assertEquals("Opal", opal.getName());
            assertEquals(OpalType.Fire, opal.getOpalType());
            assertEquals(0.95, opal.getFireIntensity());
            assertEquals(140.0, opal.getTotalPrice());
        }
    }

}
