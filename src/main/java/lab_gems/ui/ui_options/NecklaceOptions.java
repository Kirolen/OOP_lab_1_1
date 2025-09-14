package lab_gems.ui.ui_options;

import lab_gems.model.Gem;
import lab_gems.model.Necklace;
import lab_gems.model.NecklaceGem;
import lab_gems.services.GemService;
import lab_gems.services.NecklaceService;
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
        int id = inputReader.readInt("Enter ID of necklace to update:");
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
        int id = inputReader.readInt("Enter ID of necklace to delete:");
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
        int necklaceId = inputReader.readInt("Enter Necklace ID:");
        Necklace necklace = necklaceService.getNecklaceById(necklaceId);
        if (necklace == null) {
            System.out.println("Necklace not found!");
            return;
        }

        int gemId = inputReader.readInt("Enter Gem ID:");
        Gem gem = gemService.getGemById(gemId);
        if (gem == null) {
            System.out.println("Gem not found!");
            return;
        }

        int quantity = inputReader.readInt("Enter quantity:");
        NecklaceGem ng = new NecklaceGem(necklace, gem, quantity);

        necklaceService.addGemToNecklace(ng);
        System.out.println("Gem added to necklace successfully!");
    }

    public static void removeGemFromNecklace() {
        int necklaceId = inputReader.readInt("Enter Necklace ID:");
        Necklace necklace = necklaceService.getNecklaceById(necklaceId);
        if (necklace == null) {
            System.out.println("Necklace not found!");
            return;
        }

        printNecklace(necklace);

        int gemId = inputReader.readInt("Enter Gem ID to remove:");
        necklaceService.removeGemFromNecklace(necklaceId, gemId);
        System.out.println("Gem removed from necklace successfully!");
    }
}
