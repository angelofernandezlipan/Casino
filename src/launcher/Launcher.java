package launcher;

import vista.VentanaLogin;
import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin());
    }
}