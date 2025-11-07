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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Guardar datos antes de salir
                SessionController.getInstance().guardarDatos();
                System.exit(0); // Cerrar la aplicación
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

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 botones ahora
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnHistorial = new JButton("Historial");
        JButton btnEstadisticas = new JButton("Estadísticas"); // Nuevo botón (V6)
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnJugar.addActionListener(e -> new VentanaJuego(nombreUsuario));

        // btnHistorial abre VentanaHistorial (No implementada aquí)
        btnHistorial.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad de Historial en desarrollo."));

        // Botón Estadísticas (V6)
        btnEstadisticas.addActionListener(e -> new VentanaEstadisticas());

        btnSalir.addActionListener(e -> {
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