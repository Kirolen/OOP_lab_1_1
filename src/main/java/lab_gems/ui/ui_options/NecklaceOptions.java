package lab_gems.ui.ui_options;

import lab_gems.model.Gem;
import lab_gems.model.Necklace;
import lab_gems.model.NecklaceGem;
import lab_gems.services.GemService;
import lab_gems.services.NecklaceService;
import lab_gems.types.GemType;
import lab_gems.ui.InputReader;

import java.util.List;

public class NecklaceOptions {

    private static InputReader inputReader = new InputReader();
    private static NecklaceService necklaceService = new NecklaceService();
    private static GemService gemService = new GemService();

    public static void setInputReader(InputReader reader) {
        inputReader = reader;
    }

    public static void setNecklaceService(NecklaceService service) {
        necklaceService = service;
    }

    public static void setGemService(GemService service) {
        gemService = service;
    }

    public static void addNecklace() {
        String name = inputReader.readString("Enter necklace name:");
        Necklace necklace = new Necklace(name);
        necklaceService.saveNecklace(necklace);
        System.out.println("Necklace added successfully!");
    }

    public static void getAllNecklaces() {
        List<Necklace> necklaces = necklaceService.getAllNecklaces();
        System.out.println("\nAll necklaces in DB:");
        for (Necklace n : necklaces) {
            printNecklace(n);
        }
    }

    private static void printNecklace(Necklace n) {
        System.out.println("-------- NECKLACE --------");
        System.out.println("ID: " + n.getId());
        System.out.println("Name: " + n.getName());
        if (n.getGems() != null) {
            System.out.println("Gems:");
            for (NecklaceGem ng : n.getGems()) {
                Gem g = ng.getGem();
                System.out.println(" - " + g.getName() + " x" + ng.getQuantity() + " (id=" + g.getId() + ")");
            }
        }
        System.out.println("--------------------------\n");
    }

    public static void updateNecklace() {
        int id = inputReader.readNonNegativeInt("Enter ID of necklace to update:");
        Necklace necklace = necklaceService.getNecklaceById(id);

        if (necklace == null) {
            System.out.println("Necklace with ID " + id + " not found!");
            return;
        }

        printNecklace(necklace);
        String newName = inputReader.readString("Enter new name (" + necklace.getName() + "):");
        if (!newName.isBlank())
            necklace.setName(newName);

        necklaceService.updateNecklace(necklace);
        System.out.println("Necklace updated successfully!");
    }

    public static void deleteNecklace() {
        int id = inputReader.readNonNegativeInt("Enter ID of necklace to delete:");
        Necklace necklace = necklaceService.getNecklaceById(id);

        if (necklace == null) {
            System.out.println("Necklace with ID " + id + " not found!");
            return;
        }

        printNecklace(necklace);

        String confirmation = inputReader.readString("Are you sure you want to delete this necklace? (yes/no):");
        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            necklaceService.deleteNecklace(necklace);
            System.out.println("Necklace deleted successfully!");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    public static void addGemToNecklace() {
        int necklaceId = inputReader.readNonNegativeInt("Enter Necklace ID:");
        Necklace necklace = necklaceService.getNecklaceById(necklaceId);
        if (necklace == null) {
            System.out.println("Necklace not found!");
            return;
        }

        int gemId = inputReader.readNonNegativeInt("Enter Gem ID:");
        Gem gem = gemService.getGemById(gemId);
        if (gem == null) {
            System.out.println("Gem not found!");
            return;
        }

        boolean exists = necklace.getGems().stream()
                .anyMatch(ng -> ng.getGem().getId().equals(gem.getId()));

        if (exists) {
            System.out.println("This gem is already in the necklace!");
            return;
        }

        int quantity = inputReader.readNonNegativeInt("Enter quantity:");
        NecklaceGem ng = new NecklaceGem(necklace, gem, quantity);

        necklaceService.addGemToNecklace(ng);
        System.out.println("Gem added to necklace successfully!");
    }

    public static void removeGemFromNecklace() {
        int necklaceId = inputReader.readNonNegativeInt("Enter Necklace ID:");
        Necklace necklace = necklaceService.getNecklaceById(necklaceId);
        if (necklace == null) {
            System.out.println("Necklace not found!");
            return;
        }

        printNecklace(necklace);

        int gemId = inputReader.readNonNegativeInt("Enter Gem ID to remove:");
        necklaceService.removeGemFromNecklace(necklaceId, gemId);
        System.out.println("Gem removed from necklace successfully!");
    }

    public static void selectGemsForNecklace() {
        boolean adding = true;

        int preciousnessChoice = inputReader
                .readIntBetweenXAndY("Choose gem precious type (1 - Precious, 2 - SemiPrecious):", 1, 2);
        GemType type = preciousnessChoice == 1 ? GemType.Precious : GemType.SemiPrecious;

        double minWeight, maxWeight;
        do {
            minWeight = inputReader.readNonNegativeDouble("Enter minimum weight (carats):");
            maxWeight = inputReader.readNonNegativeDouble("Enter maximum weight (carats):");
            if (maxWeight < minWeight) {
                System.out.println("Maximum weight cannot be less than minimum weight! Try again.");
            }
        } while (maxWeight < minWeight);

        double minPrice, maxPrice;
        do {
            minPrice = inputReader.readNonNegativeDouble("Enter minimum price per carat:");
            maxPrice = inputReader.readNonNegativeDouble("Enter maximum price per carat:");
            if (maxPrice < minPrice) {
                System.out.println("Maximum price cannot be less than minimum price! Try again.");
            }
        } while (maxPrice < minPrice);

        double[] minMax = inputReader.readMinAndMaxTransparency();
        double minTransparency = minMax[0];
        double maxTransparency = minMax[1];

        List<Gem> gems = gemService.getGemsByCriteria(type, minWeight, maxWeight, minPrice, maxPrice,
                minTransparency, maxTransparency);

        if (gems.isEmpty()) {
            System.out.println("No gems found for the specified criteria.");
        } else {
            System.out.println("Found gems:");
            for (Gem g : gems) {
                GemOptions.printGem(g);
            }
        }

        while (adding) {
            String addChoice = inputReader.readString("Do you want to add a gem to the necklace? (yes/no):");

            if (addChoice.equalsIgnoreCase("yes") || addChoice.equalsIgnoreCase("y")) {
                NecklaceOptions.addGemToNecklace();
            } else {
                break;
            }
        }
    }

    public static double[] calculateAndShowTotals() {
        int necklaceId = inputReader.readNonNegativeInt("Enter Necklace ID:");
        Necklace necklace = necklaceService.getNecklaceById(necklaceId);

        if (necklace == null) {
            System.out.println("Necklace not found!");
            return new double[] { 0.0, 0.0 };
        }

        List<NecklaceGem> gems = necklace.getGems();
        if (gems == null || gems.isEmpty()) {
            System.out.println("This necklace has no gems.");
            return new double[] { 0.0, 0.0 };
        }

        double totalWeight = 0.0;
        double totalCost = 0.0;

        for (NecklaceGem ng : gems) {
            Gem gem = ng.getGem();
            int qty = ng.getQuantity();
            totalWeight += gem.getWeightCarat() * qty;
            totalCost += gem.getPricePerCarat() * gem.getWeightCarat() * qty;
        }

        System.out.println("Total weight (carats): " + totalWeight);
        System.out.println("Total cost: " + totalCost);

        return new double[] { totalWeight, totalCost };
    }

    public static void sortAndShowGemsByValue() {
        int necklaceId = inputReader.readNonNegativeInt("Enter Necklace ID:");
        Necklace necklace = necklaceService.getNecklaceById(necklaceId);

        if (necklace == null) {
            System.out.println("Necklace not found!");
            return;
        }

        List<NecklaceGem> gems = necklace.getGems();
        if (gems == null || gems.isEmpty()) {
            System.out.println("This necklace has no gems.");
            return;
        }

        gems.sort((ng1, ng2) -> {
            double value1 = ng1.getGem().getPricePerCarat() * ng1.getGem().getWeightCarat();
            double value2 = ng2.getGem().getPricePerCarat() * ng2.getGem().getWeightCarat();
            return Double.compare(value2, value1);
        });

        System.out.println("Gems sorted by value:");
        for (NecklaceGem ng : gems) {
            Gem gem = ng.getGem();
            double value = gem.getPricePerCarat() * gem.getWeightCarat();
            System.out.println("- " + gem.getName() + " (Value per stone: " + value + ")");
        }
    }
}
