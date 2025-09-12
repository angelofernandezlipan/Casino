import javax.swing.*;
import java.awt.*;

public class VentanaMenu {
    private final JFrame frame = new JFrame("Menú - Casino Black Cat");
    private final String nombreJugador;

    public VentanaMenu(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        configurarVentana();
    }

    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 1));

        JLabel lblBienvenida = new JLabel("Bienvenido: " + nombreJugador, SwingConstants.CENTER);
        JButton btnRuleta = new JButton("Jugar Ruleta");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnRuleta.addActionListener(e -> {
            frame.dispose();
            new VentanaRuleta(nombreJugador).mostrarVentana();
        });

        btnSalir.addActionListener(e -> {
            frame.dispose();
            new VentanaLogin().mostrarVentana();
        });

        frame.add(lblBienvenida);
        frame.add(btnRuleta);
        frame.add(btnSalir);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
