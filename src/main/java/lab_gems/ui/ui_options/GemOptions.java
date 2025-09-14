package lab_gems.ui.ui_options;

import java.util.List;

import lab_gems.model.Amethyst;
import lab_gems.model.Gem;
import lab_gems.model.Opal;
import lab_gems.model.Sapphire;
import lab_gems.model.Topaz;
import lab_gems.services.GemService;
import lab_gems.types.GemType;
import lab_gems.types.OpalType;
import lab_gems.ui.InputReader;

public class GemOptions {
    private static InputReader inputReader = new InputReader();
    private static GemService gemService = new GemService();

    public static void setInputReader(InputReader reader) {
        inputReader = reader;
    }

    public static void setGemService(GemService service) {
        gemService = service;
    }

    public static void addGem() {
        String name = inputReader.readString("Enter gem name:");

        int gemSpeciesChoice = inputReader.readIntBetweenXAndY("Choose gem type (0 - Default, 1 - Opal, 2 - Amethyst, 3 - Topaz, 4 - Sapphire):", 0, 4);

        int preciousnessChoice = inputReader.readIntBetweenXAndY("Choose gem precious type (1 - Precious, 2 - SemiPrecious):", 1, 2);
        GemType type = preciousnessChoice == 1 ? GemType.Precious : GemType.SemiPrecious;

        double weightCarat = inputReader.readNonNegativeDouble("Enter gem weight (carats):");
        double pricePerCarat = inputReader.readNonNegativeDouble("Enter price per carat:");

        double transparency = inputReader.readDoubleBetweenXAndY("Enter transparency (0.0 - 1.0):", 0.0, 1.0);

        String color = inputReader.readString("Enter gem color:");

        Gem gem;

        switch (gemSpeciesChoice) {
            case 1:
                System.out.println("=== Additional fields for Opal ===");
                int opalTypeChoice = -1;
                while (opalTypeChoice < 1 || opalTypeChoice > 5) {
                    opalTypeChoice = inputReader
                            .readNonNegativeInt("Choose Opal type (1 - White, 2 - Black, 3 - Fire, 4 - Boulder, 5 - Crystal):");
                    if (opalTypeChoice < 1 || opalTypeChoice > 5) {
                        System.out.println("Incorrect Opal type! Try again!");
                    }
                }
                OpalType opalType = (opalTypeChoice == 1) ? OpalType.White
                        : (opalTypeChoice == 2) ? OpalType.Black
                                : (opalTypeChoice == 3) ? OpalType.Fire
                                        : (opalTypeChoice == 4) ? OpalType.Boulder
                                                : OpalType.Crystal;

                double fireIntensity = inputReader.readDoubleBetweenXAndY("Enter fire intensity (0.0 - 1.0):", 0.0, 1.0);
                gem = new Opal(name, type, weightCarat, pricePerCarat, transparency, color, opalType, fireIntensity);
                break;
            case 2:
                System.out.println("=== Additional fields for Amethyst ===");
                double clarity = inputReader.readDoubleBetweenXAndY("Enter clarity (0.0 - 1.0):", 0.0, 1.0);
                double colorIntensity = inputReader.readDoubleBetweenXAndY("Enter color intensity (0.0 - 1.0):", 0.0, 1.0);
                gem = new Amethyst(
                        name, type, weightCarat, pricePerCarat, transparency, color, clarity, colorIntensity);
                break;
            case 3:
                System.out.println("=== Additional fields for Topaz ===");
                int topazHardness = inputReader.readIntBetweenXAndY("Enter hardness (1 - 10):", 1, 10);
                double lightReflectivity = inputReader.readDoubleBetweenXAndY("Enter light reflectivity (0.0 - 1.0):", 0.0, 1.0);
                gem = new Topaz(name, type, weightCarat, pricePerCarat, transparency, color,
                        topazHardness, lightReflectivity);
                break;
            case 4:
                System.out.println("=== Additional fields for Sapphire ===");
                int sapphireHardness = inputReader.readIntBetweenXAndY("Enter hardness (1 - 10):", 1, 10);
                double brilliance = inputReader.readDoubleBetweenXAndY("Enter brilliance (0.0 - 1.0):", 0.0, 1.0);
                gem = new Sapphire(name, type, weightCarat, pricePerCarat, transparency, color,
                        sapphireHardness, brilliance);
                break;
            case 0:
            default:
                gem = new Gem(name, type, weightCarat, pricePerCarat, transparency, color);
        }

        gemService.saveGem(gem);
    }

    public static void getAllGems() {
        List<Gem> gems = gemService.getAllGems();

        System.out.println("\nAll gems in DB:");
        for (Gem g : gems) {
            printGem(g);
        }
    }

    public static void updateGem() {
        int id = inputReader.readNonNegativeInt("Enter ID of gem to update:");
        Gem gem = gemService.getGemById(id);

        if (gem == null) {
            System.out.println("Gem with ID " + id + " not found!");
            return;
        }

        printGem(gem);

        String newName = inputReader.readString("Enter new name (" + gem.getName() + "):");
        if (!newName.isBlank())
            gem.setName(newName);

        int preciousnessChoice = inputReader.readIntBetweenXAndY("Choose gem precious type (1 - Precious, 2 - SemiPrecious) (" + gem.getType() + "):", 1, 2);
        gem.setType(preciousnessChoice == 1 ? GemType.Precious : GemType.SemiPrecious);

        gem.setWeightCarat(inputReader.readNonNegativeDouble("Enter new weight in carats (" + gem.getWeightCarat() + "):"));
        gem.setPricePerCarat(inputReader.readNonNegativeDouble("Enter new price per carat (" + gem.getPricePerCarat() + "):"));
        gem.setTransparency(inputReader.readDoubleBetweenXAndY("Enter new transparency (0.0 - 1.0) (" + gem.getTransparency() + "):", 0.0, 1.0));

        String colorInput = inputReader.readString("Enter new color (" + gem.getColor() + "):");
        if (!colorInput.isBlank())
            gem.setColor(colorInput);

        if (gem instanceof Opal) {
            Opal opal = (Opal) gem;
            String opalTypeInput = inputReader
                    .readString("Enter Opal type (White, Black, Fire, Boulder, Crystal) (" + opal.getOpalType() + "):");
            if (!opalTypeInput.isBlank())
                opal.setOpalType(OpalType.valueOf(opalTypeInput));
            opal.setFireIntensity(inputReader.readDoubleBetweenXAndY("Enter fire intensity (0.0 - 1.0) (" + opal.getFireIntensity() + ")", 0.0, 1.0));
        } else if (gem instanceof Amethyst) {
            Amethyst am = (Amethyst) gem;
            am.setClarity(inputReader.readDoubleBetweenXAndY("Enter clarity (" + am.getClarity() + ")", 0.0, 1.0));
            am.setColorIntensity(inputReader.readDoubleBetweenXAndY("Enter color intensity (0.0 - 1.0) (" + am.getColorIntensity() + ")", 0.0, 1.0));
        } else if (gem instanceof Topaz) {
            Topaz top = (Topaz) gem;
            top.setHardness(inputReader.readIntBetweenXAndY("Enter hardness (1 - 10) (" + top.getHardness() + ")", 1, 10));
            top.setLightReflectivity(inputReader.readDoubleBetweenXAndY("Enter light reflectivity (0.0 - 1.0) (" + top.getLightReflectivity() + ")", 0.0, 1.0));
        } else if (gem instanceof Sapphire) {
            Sapphire sap = (Sapphire) gem;
            sap.setHardness(inputReader.readIntBetweenXAndY("Enter hardness (1 - 10) (" + sap.getHardness() + ")", 1, 10));
            sap.setBrilliance(inputReader.readDoubleBetweenXAndY("Enter brilliance (0.0 - 1.0) (" + sap.getBrilliance() + ")", 0.0, 1.0));
        }

        gemService.updateGem(gem);
        System.out.println("Gem updated successfully!");
    }

    public static void deleteGem() {
        int id = inputReader.readNonNegativeInt("Enter ID of gem to delete:");
        Gem gem = gemService.getGemById(id);

        if (gem == null) {
            System.out.println("Gem with ID " + id + " not found!");
            return;
        }

        printGem(gem);

        String confirmation = inputReader.readString("Are you sure you want to delete this gem? (yes/no):");
        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            gemService.deleteGem(id);
            System.out.println("Gem deleted successfully!");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    public static void sortGems() {
        System.out.println("Choose sorting parameter:");
        System.out.println("1. Weight (carat)");
        System.out.println("2. Price per carat");
        System.out.println("3. Transparency");
        int choice = inputReader.readNonNegativeInt("Select option:");

        String field;
        switch (choice) {
            case 1:
                field = "weightCarat";
                break;
            case 2:
                field = "pricePerCarat";
                break;
            case 3:
                field = "transparency";
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        List<Gem> sortedGems = gemService.sortGemsBy(field, false); // ASC
        sortedGems.forEach(g -> printGem(g));
    }

    public static void filterGemsByTransparency() {
        double[] minMax = inputReader.readMinAndMaxTransparency();
        double min = minMax[0];
        double max = minMax[1];

        List<Gem> filtered = gemService.filterGemsByTransparency(min, max);

        if (filtered.isEmpty()) {
            System.out.println("No gems found with transparency in range [" + min + ", " + max + "]");
        } else {
            filtered.forEach(GemOptions::printGem);
        }
    }

    public static void showGemHierarchy() {
        List<Gem> gems = gemService.getAllGems();
        System.out.println("\n=== Gems by Hierarchy ===");

        System.out.println("\nPrecious Gems:");
        gems.stream()
                .filter(g -> g.getType() == GemType.Precious)
                .forEach(GemOptions::printGem);

        System.out.println("\nSemi-Precious Gems:");
        gems.stream()
                .filter(g -> g.getType() == GemType.SemiPrecious)
                .forEach(GemOptions::printGem);
    }



    public static void printGem(Gem g) {
        System.out.println("-------- GEM --------");
        System.out.println("ID: " + g.getId());
        System.out.println("Name: " + g.getName());
        System.out.println("Type: " + g.getType());
        System.out.println("Weight (carat): " + g.getWeightCarat());
        System.out.println("Price per carat: " + g.getPricePerCarat());
        System.out.println("Transparency: " + g.getTransparency());
        System.out.println("Color: " + g.getColor());

        if (g instanceof Opal) {
            Opal opal = (Opal) g;
            System.out.println("Opal type: " + opal.getOpalType());
            System.out.println("Fire intensity: " + opal.getFireIntensity());
        } else if (g instanceof Amethyst) {
            Amethyst am = (Amethyst) g;
            System.out.println("Clarity: " + am.getClarity());
            System.out.println("Color intensity: " + am.getColorIntensity());
        } else if (g instanceof Topaz) {
            Topaz top = (Topaz) g;
            System.out.println("Hardness: " + top.getHardness());
            System.out.println("Light reflectivity: " + top.getLightReflectivity());
        } else if (g instanceof Sapphire) {
            Sapphire sap = (Sapphire) g;
            System.out.println("Hardness: " + sap.getHardness());
            System.out.println("Brilliance: " + sap.getBrilliance());
        }
        System.out.println("---------------------\n");
    }
}
