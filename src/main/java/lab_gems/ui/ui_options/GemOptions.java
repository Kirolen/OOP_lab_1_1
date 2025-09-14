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

        int gemSpeciesChoice = -1;
        while (gemSpeciesChoice < 0 || gemSpeciesChoice > 4) {
            gemSpeciesChoice = inputReader.readInt(
                    "Choose gem type (0 - Default, 1 - Opal, 2 - Amethyst, 3 - Topaz, 4 - Sapphire):");
            if (gemSpeciesChoice < 0 || gemSpeciesChoice > 4) {
                System.out.println("Incorrect type! Try again!");
            }
        }

        int preciousnessChoice = -1;
        while (preciousnessChoice != 1 && preciousnessChoice != 2) {
            preciousnessChoice = inputReader.readInt("Choose gem precious type (1 - Precious, 2 - SemiPrecious):");
            if (preciousnessChoice != 1 && preciousnessChoice != 2) {
                System.out.println("Incorrect type! Try again!");
            }
        }
        GemType type = preciousnessChoice == 1 ? GemType.Precious : GemType.SemiPrecious;

        double weightCarat = inputReader.readDouble("Enter gem weight (carats):");
        double pricePerCarat = inputReader.readDouble("Enter price per carat:");

        double transparency = -1.0;
        while (transparency < 0.0 || transparency > 1.0) {
            transparency = inputReader.readDouble("Enter transparency (0.0 - 1.0):");
            if (transparency < 0.0 || transparency > 1.0) {
                System.out.println("Transparency must be between 0.0 and 1.0!");
            }
        }

        String color = inputReader.readString("Enter gem color:");

        Gem gem;

        switch (gemSpeciesChoice) {
            case 1:
                System.out.println("=== Additional fields for Opal ===");
                int opalTypeChoice = -1;
                while (opalTypeChoice < 1 || opalTypeChoice > 5) {
                    opalTypeChoice = inputReader
                            .readInt("Choose Opal type (1 - White, 2 - Black, 3 - Fire, 4 - Boulder, 5 - Crystal):");
                    if (opalTypeChoice < 1 || opalTypeChoice > 5) {
                        System.out.println("Incorrect Opal type! Try again!");
                    }
                }
                OpalType opalType = (opalTypeChoice == 1) ? OpalType.White
                        : (opalTypeChoice == 2) ? OpalType.Black
                                : (opalTypeChoice == 3) ? OpalType.Fire
                                        : (opalTypeChoice == 4) ? OpalType.Boulder
                                                : OpalType.Crystal;

                double fireIntensity = inputReader.readDouble("Enter fire intensity:");
                gem = new Opal(name, type, weightCarat, pricePerCarat, transparency, color, opalType, fireIntensity);
                break;
            case 2:
                System.out.println("=== Additional fields for Amethyst ===");
                double clarity = inputReader.readDouble("Enter clarity (0.0 - 1.0):");
                double colorIntensity = inputReader.readDouble("Enter color intensity (0.0 - 1.0):");
                gem = new Amethyst(
                        name, type, weightCarat, pricePerCarat, transparency, color, clarity, colorIntensity);
                break;
            case 3:
                System.out.println("=== Additional fields for Topaz ===");
                double topazHardness = inputReader.readDouble("Enter hardness:");
                double lightReflectivity = inputReader.readDouble("Enter light reflectivity:");
                gem = new Topaz(name, type, weightCarat, pricePerCarat, transparency, color,
                        topazHardness, lightReflectivity);
                break;
            case 4:
                System.out.println("=== Additional fields for Sapphire ===");
                double sapphireHardness = inputReader.readDouble("Enter hardness:");
                double brilliance = inputReader.readDouble("Enter brilliance:");
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
        int id = inputReader.readInt("Enter ID of gem to update:");
        Gem gem = gemService.getGemById(id);

        if (gem == null) {
            System.out.println("Gem with ID " + id + " not found!");
            return;
        }

        printGem(gem);

        String newName = inputReader.readString("Enter new name (" + gem.getName() + "):");
        if (!newName.isBlank())
            gem.setName(newName);

        String typeChoice = inputReader
                .readString("Choose gem precious type (1 - Precious, 2 - SemiPrecious) (" + gem.getType() + "):");
        if (!typeChoice.isBlank()) {
            int choice = Integer.parseInt(typeChoice);
            if (choice == 1)
                gem.setType(GemType.Precious);
            else if (choice == 2)
                gem.setType(GemType.SemiPrecious);
        }

        gem.setWeightCarat(readDoubleWithFallback("Enter new weight in carats", gem.getWeightCarat()));
        gem.setPricePerCarat(readDoubleWithFallback("Enter new price per carat", gem.getPricePerCarat()));
        gem.setTransparency(readDoubleWithFallback("Enter new transparency (0.0-1.0)", gem.getTransparency()));

        String colorInput = inputReader.readString("Enter new color (" + gem.getColor() + "):");
        if (!colorInput.isBlank())
            gem.setColor(colorInput);

        if (gem instanceof Opal) {
            Opal opal = (Opal) gem;
            String opalTypeInput = inputReader
                    .readString("Enter Opal type (White, Black, Fire, Boulder, Crystal) (" + opal.getOpalType() + "):");
            if (!opalTypeInput.isBlank())
                opal.setOpalType(OpalType.valueOf(opalTypeInput));
            opal.setFireIntensity(readDoubleWithFallback("Enter fire intensity", opal.getFireIntensity()));
        } else if (gem instanceof Amethyst) {
            Amethyst am = (Amethyst) gem;
            am.setClarity(readDoubleWithFallback("Enter clarity", am.getClarity()));
            am.setColorIntensity(readDoubleWithFallback("Enter color intensity", am.getColorIntensity()));
        } else if (gem instanceof Topaz) {
            Topaz top = (Topaz) gem;
            top.setHardness(readDoubleWithFallback("Enter hardness", top.getHardness()));
            top.setLightReflectivity(readDoubleWithFallback("Enter light reflectivity", top.getLightReflectivity()));
        } else if (gem instanceof Sapphire) {
            Sapphire sap = (Sapphire) gem;
            sap.setHardness(readDoubleWithFallback("Enter hardness", sap.getHardness()));
            sap.setBrilliance(readDoubleWithFallback("Enter brilliance", sap.getBrilliance()));
        }

        gemService.updateGem(gem);
        System.out.println("Gem updated successfully!");
    }

    public static void deleteGem() {
        int id = inputReader.readInt("Enter ID of gem to delete:");
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
        int choice = inputReader.readInt("Select option:");

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
        double min = -1.0;
        double max = -1.0;

        while (min < 0.0 || min > 1.0) {
            min = inputReader.readDouble("Enter minimum transparency (0.0 - 1.0):");
            if (min < 0.0 || min > 1.0) {
                System.out.println("Invalid value! Must be between 0.0 and 1.0.");
            }
        }

        while (max < 0.0 || max > 1.0 || max < min) {
            max = inputReader.readDouble("Enter maximum transparency (0.0 - 1.0):");
            if (max < 0.0 || max > 1.0) {
                System.out.println("Invalid value! Must be between 0.0 and 1.0.");
            } else if (max < min) {
                System.out.println("Maximum cannot be less than minimum!");
            }
        }

        List<Gem> filtered = gemService.filterGemsByTransparency(min, max);

        if (filtered.isEmpty()) {
            System.out.println("No gems found with transparency in range [" + min + ", " + max + "]");
        } else {
            filtered.forEach(GemOptions::printGem);
        }
    }

    private static Double readDoubleWithFallback(String prompt, Double currentValue) {
        String input = inputReader.readString(prompt + " (" + currentValue + "):");
        if (input.isBlank())
            return currentValue;
        input = input.replace(',', '.');
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping previous value.");
            return currentValue;
        }
    }

    private static void printGem(Gem g) {
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
