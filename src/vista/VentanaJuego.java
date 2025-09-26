package vista;

import modelo.Ruleta;
import modelo.TipoApuesta;
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
    private JButton btnGirar; // <-- Se declara como atributo de la clase

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
        btnGirar = new JButton("Girar"); // <-- Se inicializa aquí

        btnGirar.addActionListener(e -> jugarRonda()); // <-- Se le asigna la acción
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
        panelPrincipal.add(btnGirar, BorderLayout.CENTER); // <-- ¡Se agrega la variable!
        panelPrincipal.add(panelResultados, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Dentro de VentanaJuego.java

    private void jugarRonda() {

        int monto = 0;
        String tipoApuestaString = "";
        TipoApuesta tipoApuestaEnum = null;

        try {
            monto = Integer.parseInt(txtMonto.getText());
            tipoApuestaString = (String) cmbTipoApuesta.getSelectedItem();
            tipoApuestaEnum = TipoApuesta.valueOf(tipoApuestaString.toUpperCase());

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "Por favor, ingresa un monto válido (solo números).");
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error en el tipo de apuesta seleccionada.");
            return;
        }

        if (monto <= 0 || monto > saldo) {
            JOptionPane.showMessageDialog(this, "Monto inválido o insuficiente.");
            return;
        }

        int numeroGirado = ruleta.girarRuleta();
        boolean acierto = ruleta.evaluarResultado(numeroGirado, tipoApuestaEnum); // <-- Ahora tipoApuestaEnum funciona

        if (acierto) {
            saldo += monto;
            lblResultado.setText("¡GANASTE! Número: " + numeroGirado + ". Saldo: $" + saldo);
        } else {
            saldo -= monto;
            lblResultado.setText("PERDISTE. Número: " + numeroGirado + ". Saldo: $" + saldo);
        }

        lblSaldo.setText("Saldo: $" + saldo);
        ruleta.registrarResultado(numeroGirado, tipoApuestaString, acierto);
    }
}