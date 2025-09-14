package lab_gems.ui;

import java.util.Scanner;

import lab_gems.ui.ui_options.GemOptions;
import lab_gems.ui.ui_options.NecklaceOptions;

public class MenuManager {
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            printMainMenu();
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    gemCRUDMenu();
                    break;
                case "2":
                    necklaceCRUDMenu();
                    break;
                case "3":
                    GemOptions.sortGems();
                    break;
                case "4":
                    GemOptions.filterGemsByTransparency();
                    break;
                case "5":
                    System.out.println("Action executed: Show hierarchy Precious/SemiPrecious");
                    break;
                case "6":
                    System.out.println("Action executed: Select gems for necklace");
                    break;
                case "7":
                    System.out.println("Action executed: Calculate total weight of necklace");
                    break;
                case "8":
                    System.out.println("Action executed: Calculate total price of necklace");
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // ==================== Main Menu ====================
    private void printMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Manage gems (CRUD)");
        System.out.println("2. Manage necklaces (CRUD)");
        System.out.println("3. Sort gems by value");
        System.out.println("4. Filter gems by transparency");
        System.out.println("5. Show hierarchy Precious/SemiPrecious");
        System.out.println("6. Select gems for necklace");
        System.out.println("7. Calculate total weight of necklace");
        System.out.println("8. Calculate total price of necklace");
        System.out.println("0. Exit");
    }

    // ==================== Gem Menu ====================
    private void gemCRUDMenu() {
        System.out.println("\n=== Gems Menu (CRUD) ===");
        System.out.println("1. View all gems");
        System.out.println("2. Add gem");
        System.out.println("3. Edit gem");
        System.out.println("4. Delete gem");
        System.out.println("0. Return to main menu");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                GemOptions.getAllGems();
                break;
            case "2":
                GemOptions.addGem();
                break;
            case "3":
                GemOptions.updateGem();
                break;
            case "4":
                GemOptions.deleteGem();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // ==================== Necklace Menu ====================
    private void necklaceCRUDMenu() {
        System.out.println("\n=== Necklaces Menu (CRUD) ===");
        System.out.println("1. View all necklaces");
        System.out.println("2. Create necklace");
        System.out.println("3. Update necklace name");
        System.out.println("4. Delete necklace");
        System.out.println("5. Add gem to necklace");
        System.out.println("6. Remove gem from necklace");
        System.out.println("0. Return to main menu");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                NecklaceOptions.getAllNecklaces();
                break;
            case "2":
                NecklaceOptions.addNecklace();
                break;
            case "3":
                NecklaceOptions.updateNecklace();
                break;
            case "4":
                NecklaceOptions.deleteNecklace();
                break;
            case "5":
                NecklaceOptions.addGemToNecklace();
                break;
            case "6":
                NecklaceOptions.removeGemFromNecklace();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
