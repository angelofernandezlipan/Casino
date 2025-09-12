import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Ventana principal del juego de ruleta con interfaz gráfica.
 * Esta nueva clase se encarga de todos los eventos de usuario.
 */
public class VentanaRuleta {
    private static final int[] NUMEROS_ROJOS = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
    private final Random rng = new Random();
    private final String nombreJugador;

    // Historial del juego
    private final List<Integer> historialNumeros = new ArrayList<>();
    private final List<Integer> historialApuestas = new ArrayList<>();
    private final List<Boolean> historialAciertos = new ArrayList<>();

    // Componentes UI
    private final JFrame frame;
    private final JLabel lblJugador;
    private final JLabel lblResultado;
    private final ButtonGroup grupoApuestas;
    private final JRadioButton rbRojo, rbNegro, rbPar, rbImpar;
    private final JTextField txtMonto;
    private final JButton btnJugar, btnEstadisticas, btnSalir;

    /**
     * Constructor de la ventana de ruleta.
     */
    public VentanaRuleta(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.frame = new JFrame("Ruleta - Casino Black Cat");
        this.lblJugador = new JLabel("Jugador: " + nombreJugador);
        this.lblResultado = new JLabel("¡Haz tu primera apuesta!");

        // Inicializar componentes
        this.grupoApuestas = new ButtonGroup();
        this.rbRojo = new JRadioButton("Rojo", true);
        this.rbNegro = new JRadioButton("Negro");
        this.rbPar = new JRadioButton("Par");
        this.rbImpar = new JRadioButton("Impar");
        this.txtMonto = new JTextField("100");
        this.btnJugar = new JButton("GIRAR RULETA");
        this.btnEstadisticas = new JButton("Ver Estadísticas");
        this.btnSalir = new JButton("Cerrar Sesión");

        configurarVentana();
        configurarEventos();
    }

    /**
     * Configura la ventana y sus componentes.
     */
    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        // Panel superior - Info del jugador
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(new Color(0, 100, 0));
        lblJugador.setForeground(Color.WHITE);
        lblJugador.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblJugador);

        // Panel central - Juego
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título del juego
        JLabel titulo = new JLabel("RULETA CASINO BLACK CAT");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        panelCentral.add(titulo, gbc);

        // Etiqueta de apuestas
        JLabel lblApuestas = new JLabel("Selecciona tu apuesta:");
        lblApuestas.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        panelCentral.add(lblApuestas, gbc);

        // Radio buttons para apuestas
        grupoApuestas.add(rbRojo);
        grupoApuestas.add(rbNegro);
        grupoApuestas.add(rbPar);
        grupoApuestas.add(rbImpar);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2; panelCentral.add(rbRojo, gbc);
        gbc.gridx = 1; panelCentral.add(rbNegro, gbc);
        gbc.gridx = 2; panelCentral.add(rbPar, gbc);
        gbc.gridx = 3; panelCentral.add(rbImpar, gbc);

        // Monto de apuesta
        JLabel lblMonto = new JLabel("Monto ($):");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panelCentral.add(lblMonto, gbc);

        txtMonto.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 1; gbc.gridy = 3;
        panelCentral.add(txtMonto, gbc);

        // Botón jugar
        btnJugar.setFont(new Font("Arial", Font.BOLD, 16));
        btnJugar.setBackground(new Color(220, 20, 20));
        btnJugar.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelCentral.add(btnJugar, gbc);

        // Resultado
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4;
        panelCentral.add(lblResultado, gbc);

        // Panel inferior - Botones de acción
        JPanel panelInferior = new JPanel(new FlowLayout());
        btnEstadisticas.setBackground(new Color(70, 130, 180));
        btnEstadisticas.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(139, 69, 19));
        btnSalir.setForeground(Color.WHITE);

        panelInferior.add(btnEstadisticas);
        panelInferior.add(btnSalir);

        // Agregar paneles al frame
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Configura los eventos de los componentes.
     */
    private void configurarEventos() {
        btnJugar.addActionListener(e -> jugarRonda());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnSalir.addActionListener(e -> cerrarSesion());

        // Enter en campo de monto
        txtMonto.addActionListener(e -> jugarRonda());
    }

    /**
     * Muestra la ventana centrada en pantalla.
     */
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Ejecuta una ronda del juego de ruleta.
     */
    private void jugarRonda() {
        try {
            // Validar monto
            int monto = Integer.parseInt(txtMonto.getText().trim());
            if (monto <= 0) {
                JOptionPane.showMessageDialog(frame,
                        "El monto debe ser mayor a 0",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener tipo de apuesta
            char tipoApuesta = obtenerTipoApuesta();

            // Deshabilitar botón mientras gira
            btnJugar.setEnabled(false);
            lblResultado.setText("Girando la ruleta...");

            // Simular giro con delay
            Timer timer = new Timer(2000, e -> {
                int numero = rng.nextInt(37); // 0 a 36
                boolean acierto = evaluarApuesta(numero, tipoApuesta);

                // Registrar resultado
                historialNumeros.add(numero);
                historialApuestas.add(monto);
                historialAciertos.add(acierto);

                // Mostrar resultado
                mostrarResultado(numero, tipoApuesta, monto, acierto);
                btnJugar.setEnabled(true);
            });
            timer.setRepeats(false);
            timer.start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Ingrese un monto válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtiene el tipo de apuesta seleccionado.
     */
    private char obtenerTipoApuesta() {
        if (rbRojo.isSelected()) return 'R';
        if (rbNegro.isSelected()) return 'N';
        if (rbPar.isSelected()) return 'P';
        return 'I'; // Impar
    }

    /**
     * Evalúa si la apuesta fue ganadora.
     */
    private boolean evaluarApuesta(int numero, char tipo) {
        switch (tipo) {
            case 'R': return esRojo(numero);
            case 'N': return !esRojo(numero) && numero != 0;
            case 'P': return numero % 2 == 0 && numero != 0;
            case 'I': return numero % 2 == 1;
            default: return false;
        }
    }

    /**
     * Determina si un número es rojo.
     */
    private boolean esRojo(int numero) {
        for (int numeroRojo : NUMEROS_ROJOS) {
            if (numero == numeroRojo) return true;
        }
        return false;
    }

    /**
     * Muestra el resultado de la ronda.
     */
    private void mostrarResultado(int numero, char tipo, int monto, boolean acierto) {
        String color = esRojo(numero) ? "ROJO" : (numero == 0 ? "VERDE" : "NEGRO");
        String paridad = numero == 0 ? "CERO" : (numero % 2 == 0 ? "PAR" : "IMPAR");
        String tipoApuestaStr = obtenerNombreApuesta(tipo);

        String mensaje = String.format("Número: %d (%s, %s)", numero, color, paridad);

        if (acierto) {
            lblResultado.setText("¡GANASTE! " + mensaje + " | Ganancia: +$" + monto);
            lblResultado.setForeground(new Color(0, 150, 0));
        } else {
            lblResultado.setText("Perdiste. " + mensaje + " | Pérdida: -$" + monto);
            lblResultado.setForeground(new Color(150, 0, 0));
        }
    }

    /**
     * Obtiene el nombre de la apuesta para mostrar.
     */
    private String obtenerNombreApuesta(char tipo) {
        switch (tipo) {
            case 'R': return "Rojo";
            case 'N': return "Negro";
            case 'P': return "Par";
            case 'I': return "Impar";
            default: return "Desconocido";
        }
    }

    /**
     * Muestra las estadísticas del juego.
     */
    private void mostrarEstadisticas() {
        if (historialNumeros.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "No hay estadísticas disponibles.\n¡Juega una ronda primero!",
                    "Sin datos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int totalRondas = historialNumeros.size();
        int totalApostado = historialApuestas.stream().mapToInt(Integer::intValue).sum();
        int aciertos = (int) historialAciertos.stream().mapToLong(b -> b ? 1 : 0).sum();
        double porcentajeAcierto = (double) aciertos / totalRondas * 100;

        int gananciaNeta = 0;
        for (int i = 0; i < totalRondas; i++) {
            int monto = historialApuestas.get(i);
            gananciaNeta += historialAciertos.get(i) ? monto : -monto;
        }

        String estadisticas = String.format(
                "=== ESTADÍSTICAS DE %s ===\n\n" +
                        "Rondas jugadas: %d\n" +
                        "Total apostado: $%d\n" +
                        "Total aciertos: %d\n" +
                        "Porcentaje de acierto: %.2f%%\n" +
                        "Ganancia/Pérdida neta: $%d",
                nombreJugador, totalRondas, totalApostado, aciertos, porcentajeAcierto, gananciaNeta
        );

        JOptionPane.showMessageDialog(frame, estadisticas,
                "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Cierra la sesión y regresa al login.
     */
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(frame,
                "¿Está seguro de que desea cerrar sesión?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            frame.dispose();
            new VentanaLogin().mostrarVentana();
        }
    }
}