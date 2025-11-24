package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import modelo.*; // Importa Ruleta, ApuestaBase, Resultado, etc.
import controlador.SessionController; // Para guardar el historial

public class VentanaJuego extends JFrame {

    // El Modelo (Ahora es la única fuente de verdad para el saldo)
    private final Ruleta ruleta;

    // Componentes de la Vista
    private JTextField txtMonto;
    private JComboBox<String> cmbTipoApuesta;
    private JLabel lblSaldo;
    private JLabel lblResultado;
    private JButton btnGirar;

    private final String nombreJugador;

    public VentanaJuego(String nombreJugador) {
        super("Ruleta - Casino Black Cat");
        this.nombreJugador = nombreJugador;

        // 1. Inicializar el modelo. Saldo inicial de 1000
        this.ruleta = new Ruleta(1000);

        // 2. Inicializar componentes (usando datos del modelo)
        initComponents();
        setupLayout();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        txtMonto = new JTextField(10);

        // Las opciones deben coincidir con los casos del switch en jugarRonda
        cmbTipoApuesta = new JComboBox<>(new String[]{"Rojo", "Negro", "Par", "Impar"});

        // IMPORTANTE: Obtenemos el saldo inicial desde la Ruleta, no de una variable local
        lblSaldo = new JLabel("Saldo: $" + ruleta.getSaldo(), SwingConstants.CENTER);

        lblResultado = new JLabel("Bienvenido, " + nombreJugador + ". Listo para jugar!", SwingConstants.CENTER);

        btnGirar = new JButton("Girar");
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
        panelPrincipal.add(btnGirar, BorderLayout.CENTER);
        panelPrincipal.add(panelResultados, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Methodo principal refinado (V10)
    private void jugarRonda() {
        // Validar formato antes de lógica
        if (txtMonto.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int monto = Integer.parseInt(txtMonto.getText());

            // Monto positivo
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Saldo suficiente
            if (monto > ruleta.getSaldo()) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipoString = (String) cmbTipoApuesta.getSelectedItem();

            ApuestaBase apuesta = switch(tipoString) {
                case "Rojo" -> new ApuestaRojo(monto);
                case "Negro" -> new ApuestaNegro(monto);
                case "Par" -> new ApuestaPar(monto);
                case "Impar" -> new ApuestaImpar(monto);
                default -> throw new IllegalStateException("Tipo de apuesta desconocido"); // Caso Excepcional (Bug)
            };

            // Llamada al modelo (El modelo igual se protege, pero la vista ya filtró lo obvio)
            Resultado resultadoRonda = ruleta.jugar(apuesta);

            // ...Resto del código de guardar y actualizar interfaz igual que antes...
            SessionController.getInstance().getRepositorio().guardar(resultadoRonda);
            // ...

        } catch (NumberFormatException e) {
            // Error esperado de entrada
            JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Excepción no controlada: Red de seguridad global
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}