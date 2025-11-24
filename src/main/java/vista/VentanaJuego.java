package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import modelo.*;
import controlador.SessionController;

public class VentanaJuego extends JFrame {

    private final Ruleta ruleta;

    // --- COMPONENTES DE LA VISTA ---
    private JTextField txtMonto;
    private JComboBox<String> cmbTipoApuesta;
    private JLabel lblSaldo;
    private JLabel lblResultado;
    private JButton btnGirar;

    private final String nombreJugador;

    public VentanaJuego(String nombreJugador) {
        super("Ruleta - Casino Black Cat");
        this.nombreJugador = nombreJugador;

        // 1. INICIALIZAR MODELO CON DINERO REAL
        // Si no pones el 1000 aquí, la ruleta empieza en 0 y siempre dará error.
        this.ruleta = new Ruleta(1000);

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

        // 2. VINCULAR ETIQUETA AL MODELO
        // Aquí nos aseguramos de que muestre lo que realmente tiene la ruleta
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

    // --- LÓGICA DE ITERACIÓN 10: VALIDACIONES ---
    private void jugarRonda() {

        // 1. ACTUALIZAR VISUALMENTE ANTES DE NADA
        // Esto asegura que si tenías 0 y la etiqueta decía 1000, se corrija ahora mismo.
        lblSaldo.setText("Saldo: $" + ruleta.getSaldo());

        // VALIDACIÓN 1: Campo vacío (Control de flujo con IF)
        if (txtMonto.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un monto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int monto = Integer.parseInt(txtMonto.getText());

            // VALIDACIÓN 2: Monto Negativo (Control de flujo con IF)
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // VALIDACIÓN 3: Saldo Insuficiente (Control de flujo con IF)
            if (monto > ruleta.getSaldo()) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente. Tienes: $" + ruleta.getSaldo(), "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- LÓGICA DEL JUEGO ---
            String tipoString = (String) cmbTipoApuesta.getSelectedItem();

            ApuestaBase apuesta = switch(tipoString) {
                case "Rojo" -> new ApuestaRojo(monto);
                case "Negro" -> new ApuestaNegro(monto);
                case "Par" -> new ApuestaPar(monto);
                case "Impar" -> new ApuestaImpar(monto);
                default -> throw new IllegalStateException("Tipo de apuesta desconocido"); // Excepción real
            };

            // Delegar al modelo
            Resultado resultadoRonda = ruleta.jugar(apuesta);

            // Guardar historial
            SessionController.getInstance().getRepositorio().guardar(resultadoRonda);

            // --- ACTUALIZAR RESULTADOS ---
            String mensaje = resultadoRonda.isAcierto() ? "¡GANASTE!" : "PERDISTE";
            lblResultado.setText(String.format("%s. Salió %d. Saldo: $%d",
                    mensaje, resultadoRonda.getNumero(), ruleta.getSaldo()));

            // Actualizar saldo final
            lblSaldo.setText("Saldo: $" + ruleta.getSaldo());

            // Verificar Game Over
            if (ruleta.getSaldo() <= 0) {
                JOptionPane.showMessageDialog(this, "¡Te has quedado sin saldo! Fin del juego.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                SessionController.getInstance().cerrarSesion();
                dispose();
                new VentanaLogin();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Red de seguridad global (Caso 6 del PDF)
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }
}