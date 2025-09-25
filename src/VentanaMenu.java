import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    private final String nombreUsuario;

    public VentanaMenu(String nombreUsuario) {
        super("Menú Principal - Casino Black Cat");
        this.nombreUsuario = nombreUsuario;

        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + nombreUsuario, SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnHistorial = new JButton("Historial");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnJugar.addActionListener(e -> new VentanaJuego(nombreUsuario));

        btnHistorial.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad de historial en desarrollo."));

        btnSalir.addActionListener(e -> {
            dispose();
            new VentanaLogin();
        });

        panelBotones.add(btnJugar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnSalir);

        add(lblBienvenida, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }
}