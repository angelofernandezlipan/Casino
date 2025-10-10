package vista;

import javax.swing.*;
import java.awt.*;

// Importar los Modelos y el Controlador para aplicar MVC
import modelo.Ruleta;
import modelo.TipoApuesta;
import modelo.Resultado;
import modelo.Usuario;
import controlador.SessionController;

public class VentanaJuego extends JFrame {

    // MODELO y Estado
    private final Ruleta ruleta;
    private final String nombreJugador;
    private int saldo = 1000; // Saldo inicial

    // VISTA (Componentes declarados a nivel de clase para tener alcance global)
    private JTextField txtMonto;
    private JComboBox<String> cmbTipoApuesta;
    private JLabel lblSaldo;
    private JLabel lblResultado;
    private JButton btnGirar;

    // CONSTRUCTOR
    public VentanaJuego(String nombreJugador) {
        super("Ruleta - Casino Black Cat");
        this.nombreJugador = nombreJugador;
        this.ruleta = new Ruleta(); // Inicialización del Modelo Ruleta

        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        // Inicialización de componentes (no declaración)
        txtMonto = new JTextField(10);
        // Opciones que deben coincidir exactamente con los valores del ENUM TipoApuesta
        cmbTipoApuesta = new JComboBox<>(new String[]{"ROJO", "NEGRO", "PAR", "IMPAR"});
        lblSaldo = new JLabel("Saldo: $" + saldo, SwingConstants.CENTER);
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

    // MÉTHOD CENTRAL DE LÓGICA DE JUEGO
    private void jugarRonda() {

        // Declaración de variables fuera del try para corregir el error de alcance (scope)
        int monto = 0;
        String tipoApuestaString = "";
        TipoApuesta tipoApuestaEnum = null;

        try {
            monto = Integer.parseInt(txtMonto.getText());
            tipoApuestaString = (String) cmbTipoApuesta.getSelectedItem();

            // Conversión al ENUM (V4)
            tipoApuestaEnum = TipoApuesta.valueOf(tipoApuestaString);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un monto válido (solo números).", "Error de Apuesta", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error: Tipo de apuesta no reconocido.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de Saldo
        if (monto <= 0 || monto > saldo) {
            JOptionPane.showMessageDialog(this, "Monto inválido o insuficiente.", "Error de Saldo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // LÓGICA DEL JUEGO (Modelos)
        int numeroGirado = ruleta.girarRuleta();
        boolean acierto = ruleta.evaluarResultado(numeroGirado, tipoApuestaEnum); // Usa el Enum

        // Actualización de Saldo (se aplica antes de registrar el resultado)
        if (acierto) {
            saldo += monto;
        } else {
            saldo -= monto;
        }

        // --- REGISTRO DEL RESULTADO (Lógica V5: Asociación y Dependencia) ---

        // 1. Obtener el usuario actual a través del controlador
        SessionController controller = SessionController.getInstance();
        Usuario usuarioActual = controller.getUsuarioActual();

        // 2. Crear el objeto Resultado (Dependencia)
        Resultado resultadoRonda = new Resultado(
                numeroGirado,
                tipoApuestaEnum,
                acierto,
                monto,
                saldo // Se registra el saldo final
        );

        // 3. Registrar el resultado en el historial del usuario (Asociación)
        if (usuarioActual != null) {
            usuarioActual.agregarResultado(resultadoRonda);
        }

        // -------------------------------------------------------------------

        // ACTUALIZACIÓN DE LA VISTA

        String mensaje = acierto ? "¡GANASTE!" : "PERDISTE";
        lblResultado.setText(String.format("%s. Número: %d (%s). Saldo: $%d", mensaje, numeroGirado, tipoApuestaEnum.name(), saldo));
        lblSaldo.setText("Saldo: $" + saldo);

        // Comprobar si el saldo es cero (condición de salida)
        if (saldo <= 0) {
            JOptionPane.showMessageDialog(this, "¡Te has quedado sin saldo! Cerrando sesión.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            controller.cerrarSesion();
            dispose();
        }
    }
}