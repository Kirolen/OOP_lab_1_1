package lab_gems.ui;

import java.util.Scanner;

public class InputReader {
    private static final Scanner scanner = new Scanner(System.in);

    public int readInt(String prompt) {
        System.out.print(prompt + " ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); 
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public double readDouble(String prompt) {
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
}
