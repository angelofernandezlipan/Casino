package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Ruleta;
import modelo.TipoApuesta;
import modelo.Resultado;
import modelo.Usuario;
import controlador.SessionController;

public class VentanaJuego extends JFrame {

    private final Ruleta ruleta;
    private final String nombreJugador;
    private int saldo = 1000;

    private JTextField txtMonto;
    private JComboBox<String> cmbTipoApuesta;
    private JLabel lblSaldo;
    private JLabel lblResultado;
    private JButton btnGirar;

    public VentanaJuego(String nombreJugador) {
        super("Ruleta - Casino Black Cat");
        this.nombreJugador = nombreJugador;
        this.ruleta = new Ruleta();

        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        txtMonto = new JTextField(10);
        cmbTipoApuesta = new JComboBox<>(new String[]{"ROJO", "NEGRO", "PAR", "IMPAR"});
        lblSaldo = new JLabel("Saldo: $" + saldo, SwingConstants.CENTER);
        lblResultado = new JLabel("Bienvenido, " + nombreJugador + ". Listo para jugar!", SwingConstants.CENTER);
        btnGirar = new JButton("Girar");

        btnGirar.addActionListener(e -> jugarRonda());
    }

    private void setupLayout() {
        // ... (Configuración de layout, sin cambios mayores)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelInputs = new JPanel(new GridLayout(2, 2, 5, 5));
        panelInputs.add(new JLabel("Tipo de apuesta:"));
        panelInputs.add(cmbTipoApuesta);
        panelInputs.add(new JLabel("Monto:"));
        panelInputs.add(txtMonto);

        JPanel panelResultados = new JPanel(new GridLayout(2, 1, 5, 5));
        panelResultados.add(lblSaldo);
        panelResultados.add(lblResultado);

        panelPrincipal.add(panelInputs, BorderLayout.NORTH);
        panelPrincipal.add(btnGirar, BorderLayout.CENTER);
        panelPrincipal.add(panelResultados, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void jugarRonda() {

        int monto = 0;
        String tipoApuestaString = "";
        TipoApuesta tipoApuestaEnum = null;

        try {
            monto = Integer.parseInt(txtMonto.getText());
            tipoApuestaString = (String) cmbTipoApuesta.getSelectedItem();
            tipoApuestaEnum = TipoApuesta.valueOf(tipoApuestaString);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un monto válido (solo números).", "Error de Apuesta", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error: Tipo de apuesta no reconocido.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (monto <= 0 || monto > saldo) {
            JOptionPane.showMessageDialog(this, "Monto inválido o insuficiente.", "Error de Saldo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // LÓGICA DEL JUEGO
        int numeroGirado = ruleta.girarRuleta();
        boolean acierto = ruleta.evaluarResultado(numeroGirado, tipoApuestaEnum);

        // Actualización de Saldo
        if (acierto) {
            saldo += monto;
        } else {
            saldo -= monto;
        }

        // REGISTRO DEL RESULTADO (Asociación y Dependencia V5)
        SessionController controller = SessionController.getInstance();
        Usuario usuarioActual = controller.getUsuarioActual();

        Resultado resultadoRonda = new Resultado(
                numeroGirado,
                tipoApuestaEnum,
                acierto,
                monto,
                saldo
        );

        if (usuarioActual != null) {
            usuarioActual.agregarResultado(resultadoRonda);
        }

        // ACTUALIZACIÓN DE LA VISTA
        String mensaje = acierto ? "¡GANASTE!" : "PERDISTE";
        lblResultado.setText(String.format("%s. Número: %d (%s). Saldo: $%d", mensaje, numeroGirado, tipoApuestaEnum.name(), saldo));
        lblSaldo.setText("Saldo: $" + saldo);

        // Comprobar saldo
        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this, "¡Te has quedado sin saldo! Cerrando sesión.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            controller.cerrarSesion();
            dispose();
            new VentanaLogin(); // Regresa al Login
        }
    }
}