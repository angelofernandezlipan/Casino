package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Ruleta;
import modelo.Resultado;
import modelo.Usuario;
import modelo.*;
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
        cmbTipoApuesta = new JComboBox<>(new String[]{"Rojo", "Negro", "Par", "Impar"});
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

    // Crear objeto Apuesta correcto
    private ApuestaBase crearApuestaDesdeSeleccion(int monto) {
        String seleccion = (String) cmbTipoApuesta.getSelectedItem();

        switch (seleccion) {
            case "Rojo":
                return new ApuestaRojo(monto);
            case "Negro":
                return new ApuestaNegro(monto);
            case "Par":
                return new ApuestaPar(monto);
            case "Impar":
                return new ApuestaImpar(monto);
            default:
                throw new IllegalArgumentException("Tipo de apuesta no válido");
        }
    }

    private void jugarRonda() {

        int monto = 0;
        ApuestaBase apuesta; // La variable ahora es del tipo de la superclase

        try {
            monto = Integer.parseInt(txtMonto.getText());
            // 1. Crea el objeto polimórfico
            apuesta = crearApuestaDesdeSeleccion(monto);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (monto <= 0 || monto > saldo) {
            JOptionPane.showMessageDialog(this, "Monto inválido o insuficiente.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lógica polimórfica
        // 2. Ruleta calcula el estado
        int numeroGirado = ruleta.girarRuleta();
        String colorGirado = ruleta.colorDe(numeroGirado);

        // 3. El objeto apuesta determina si acertó (Polimorfismo)
        boolean acierto = apuesta.acierta(numeroGirado, colorGirado);

        // 4. Actualización de Saldo
        if (acierto) {
            saldo += apuesta.getMontoApostado();
        } else {
            saldo -= apuesta.getMontoApostado();
        }

        // 5. Registro del resultado (Versión 5)
        SessionController controller = SessionController.getInstance();
        Usuario usuarioActual = controller.getUsuarioActual();

        // El constructor de Resultado ahora toma el objeto ApuestaBase
        Resultado resultadoRonda = new Resultado(
                numeroGirado,
                apuesta, // Pasa el objeto apuesta completo
                acierto,
                saldo
        );

        if (usuarioActual != null) {
            usuarioActual.agregarResultado(resultadoRonda);
        }
        // Acá se acaba la lógica polimórfica

        // 6. Se actualiza la vista
        String mensaje = acierto ? "¡GANASTE!" : "PERDISTE";
        lblResultado.setText(String.format("%s. Salió el %d %s. Saldo: $%d", mensaje, numeroGirado, colorGirado, saldo));
        lblSaldo.setText("Saldo: $" + saldo);

        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this,
                    "¡Te has quedado sin saldo! Cerrando sesión.",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);

            SessionController.getInstance().cerrarSesion();

            dispose(); // Cerrar

            new VentanaLogin();
        }
    }
}