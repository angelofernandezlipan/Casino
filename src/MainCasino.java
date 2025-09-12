import javax.swing.*;
import java.awt.*;

public class MainCasino {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VentanaLogin().mostrarVentana();
            }
        });
    }
}