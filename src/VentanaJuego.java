import javax.swing.*;
import java.awt.*;

public class VentanaJuego extends JFrame {

    private final Ruleta ruleta;
    private final String nombreJugador;
    private int saldo = 1000;

    private JTextField txtMonto;
    private JComboBox<String> cmbTipoApuesta;
    private JLabel lblSaldo;
    private JLabel lblResultado;

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
        lblResultado = new JLabel("Listo para jugar!", SwingConstants.CENTER);
        JButton btnGirar = new JButton("Girar");

        btnGirar.addActionListener(e -> jugarRonda());
    }

    private void setupLayout() {
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
        panelPrincipal.add(new JButton("Girar"), BorderLayout.CENTER);
        panelPrincipal.add(panelResultados, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void jugarRonda() {
        try {
            // 1. Obtener el texto del campo y convertirlo a un entero
            int monto = Integer.parseInt(txtMonto.getText());
            String tipoApuesta = (String) cmbTipoApuesta.getSelectedItem();

            // 2. Validar que el monto sea válido
            if (monto <= 0 || monto > saldo) {
                JOptionPane.showMessageDialog(this, "Monto inválido o insuficiente.");
                return;
            }

            // 3. Llamar a la lógica del juego con el monto ya convertido
            int numeroGirado = ruleta.girarRuleta();
            boolean acierto = ruleta.evaluarResultado(numeroGirado, tipoApuesta);

            if (acierto) {
                saldo += monto;
                lblResultado.setText("¡GANASTE! Número: " + numeroGirado + ". Saldo: $" + saldo);
            } else {
                saldo -= monto;
                lblResultado.setText("PERDISTE. Número: " + numeroGirado + ". Saldo: $" + saldo);
            }

            lblSaldo.setText("Saldo: $" + saldo);
            ruleta.registrarResultado(numeroGirado, tipoApuesta, acierto);

        } catch (NumberFormatException e) {
            // 4. Capturar el error si el usuario no ingresa un número
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un monto válido (solo números).");
        }
    }
}