package lab_gems.ui;

import java.util.Scanner;

public class InputReader {
    private static final Scanner scanner = new Scanner(System.in);

    private int readInt(String prompt) {
        System.out.print(prompt + " ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double readDouble(String prompt) {
        System.out.print(prompt + " ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public boolean readBoolean(String prompt) {
        System.out.print(prompt + " (true/false, or y/n) ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("true") || input.equals("y") || input.equals("yes");
    }

    public String readString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    public double[] readMinAndMaxTransparency() {
        double min, max;

        do {
            min = readDouble("Enter minimum transparency (0.0 - 1.0):");
            if (min < 0.0 || min > 1.0) {
                System.out.println("Invalid value! Must be between 0.0 and 1.0.");
            }
        } while (min < 0.0 || min > 1.0);

        do {
            max = readDouble("Enter maximum transparency (0.0 - 1.0):");
            if (max < 0.0 || max > 1.0) {
                System.out.println("Invalid value! Must be between 0.0 and 1.0.");
            } else if (max < min) {
                System.out.println("Maximum cannot be less than minimum!");
            }
        } while (max < 0.0 || max > 1.0 || max < min);

        return new double[] { min, max };
    }

    public double readNonNegativeDouble(String prompt) {
        double value;
        do {
            value = readDouble(prompt);
            if (value < 0.0) {
                System.out.println("Invalid value! Must be 0.0 or greater.");
            }
        } while (value < 0.0);

        return value;
    }

    public int readNonNegativeInt(String prompt) {
        int value;
        do {
            value = readInt(prompt);
            if (value < 0) {
                System.out.println("Invalid value! Must be 0 or greater.");
            }
        } while (value < 0);
        return value;
    }

    public double readDoubleBetweenXAndY(String prompt, double x, double y) {
        double value = x - 1;
        while (value < x || value > y) {
            value = readNonNegativeDouble(prompt);
            if (value < x || value > y) {
                System.out.println("Value must be between " + x + " and " + y + "!");
            }
        }
        return value;
    }

    public int readIntBetweenXAndY(String prompt, int x, int y) {
        int value = x - 1;
        while (value < x || value > y) {
            value = readNonNegativeInt(prompt);
            if (value < x || value > y) {
                System.out.println("Value must be between " + x + " and " + y + "!");
            }
        }
        return value;
    }

}
