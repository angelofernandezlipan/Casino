package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import modelo.*; // Importa Ruleta, ApuestaBase, Resultado, etc.
import controlador.SessionController; // Para guardar el historial

public class VentanaJuego extends JFrame {

    // EL MODELO (Ahora es la única fuente de verdad para el saldo)
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

        // 1. INICIALIZAR EL MODELO PRIMERO
        // Definimos un saldo inicial de 1000 para la sesión
        this.ruleta = new Ruleta(1000);

        // 2. INICIALIZAR COMPONENTES (Usando datos del modelo)
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

    // --- MÉTHODO PRINCIPAL REFACTORIZADO (V9) ---
    private void jugarRonda() {
        try {
            int monto = Integer.parseInt(txtMonto.getText());
            String tipoString = (String) cmbTipoApuesta.getSelectedItem();

            // 1. Crear la apuesta (Polimorfismo V7)
            ApuestaBase apuesta = switch(tipoString) {
                case "Rojo" -> new ApuestaRojo(monto);
                case "Negro" -> new ApuestaNegro(monto);
                case "Par" -> new ApuestaPar(monto);
                case "Impar" -> new ApuestaImpar(monto);
                default -> null;
            };

            // 2. DELEGAR AL MODELO (V9)
            // El méthodo jugar() de Ruleta ahora valida el saldo y la apuesta.
            // Si algo está mal, lanzará una excepción que atrapamos abajo.
            Resultado resultadoRonda = ruleta.jugar(apuesta);

            // 3. GUARDAR EN HISTORIAL (V8 - Persistencia)
            SessionController.getInstance().getRepositorio().guardar(resultadoRonda);

            // 4. ACTUALIZAR VISTA
            String mensaje = resultadoRonda.isAcierto() ? "¡GANASTE!" : "PERDISTE";
            lblResultado.setText(String.format("%s. Salió %d. Saldo: $%d",
                    mensaje, resultadoRonda.getNumero(), ruleta.getSaldo()));

            // Actualizar etiqueta de saldo consultando al modelo
            lblSaldo.setText("Saldo: $" + ruleta.getSaldo());

            // 5. VERIFICAR GAME OVER
            if (ruleta.getSaldo() <= 0) {
                JOptionPane.showMessageDialog(this,
                        "¡Te has quedado sin saldo! Cerrando sesión.",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);

                SessionController.getInstance().cerrarSesion();
                dispose();
                new VentanaLogin();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido (ingrese solo números).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            // Captura las validaciones de negocio del Modelo (Saldo insuficiente, monto negativo, etc.)
            JOptionPane.showMessageDialog(this, e.getMessage(), "Regla de Negocio", JOptionPane.WARNING_MESSAGE);
        }
    }
}