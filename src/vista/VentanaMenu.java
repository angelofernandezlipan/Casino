package vista;

import javax.swing.*;
import java.awt.*;
import controlador.SessionController;

public class VentanaMenu extends JFrame {

    private final String nombreUsuario;

    public VentanaMenu(String nombreUsuario) {
        super("Menú Principal - Casino Black Cat");
        this.nombreUsuario = nombreUsuario;

        initComponents();

        // V7/V8: Asegura que la persistencia se ejecute al cerrar
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // V8: cerrarSesion() guarda tanto usuarios como historial
                SessionController.getInstance().cerrarSesion();
                System.exit(0); // Cierra la aplicación
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + nombreUsuario, SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnHistorial = new JButton("Historial"); // (Asume VentanaHistorial V5)
        JButton btnEstadisticas = new JButton("Estadísticas"); // (V6)
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnJugar.addActionListener(e -> new VentanaJuego(nombreUsuario));

        btnHistorial.addActionListener(e -> {
            // Implementación de VentanaHistorial (V5)
            JOptionPane.showMessageDialog(this, "Funcionalidad de Historial en desarrollo.");
        });

        // V6: Llama a la ventana de estadísticas
        btnEstadisticas.addActionListener(e -> new VentanaEstadisticas());

        btnSalir.addActionListener(e -> {
            // V8: Cierra la sesión (guarda datos) antes de ir al login
            SessionController.getInstance().cerrarSesion();
            dispose();
            new VentanaLogin();
        });

        panelBotones.add(btnJugar);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnSalir);

        add(lblBienvenida, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }
}