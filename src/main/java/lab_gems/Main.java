package lab_gems;

import java.util.logging.LogManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import lab_gems.ui.MenuManager;

public class Main {
    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        Logger.getLogger("").setLevel(Level.SEVERE);

        MenuManager menu = new MenuManager();
        menu.run();
    }
}
