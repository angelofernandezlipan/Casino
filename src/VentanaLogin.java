import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la ventana de Login del Casino Black Cat.
 * Permite ingresar credenciales para acceder al sistema.
 */
public class VentanaLogin {
    // --- Lista dinámica de usuarios ---
    public static final List<Usuario> USUARIOS = new ArrayList<>();

    // --- UI ---
    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Registrarse");

    /**
     * Constructor que inicializa la ventana de login.
     * Configura el tamaño, los componentes y los eventos.
     */
    public VentanaLogin() {
        inicializarUsuarios();
        configurarVentana();
        configurarEventos();
    }

    /**
     * Inicializa usuarios hardcodeados para pruebas.
     */
    private void inicializarUsuarios() {
        USUARIOS.add(new Usuario("admin", "1234", "Don Donnie"));
        USUARIOS.add(new Usuario("jugador1", "pass", "Juan Pérez"));
        USUARIOS.add(new Usuario("test", "test", "Usuario de Prueba"));
    }

    /**
     * Configura la ventana y sus componentes.
     */
    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titulo = new JLabel("CASINO BLACK CAT", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        frame.add(titulo, gbc);

        // Usuario
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(lblUsuario, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(txtUsuario, gbc);

        // Clave
        gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(lblClave, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(txtClave, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(btnIngresar, gbc);
        gbc.gridx = 1;
        frame.add(btnRegistrar, gbc);
    }

    /**
     * Configura los eventos de los botones.
     */
    private void configurarEventos() {
        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirRegistro());

        // Enter en campos de texto
        txtUsuario.addActionListener(e -> txtClave.requestFocus());
        txtClave.addActionListener(e -> login());
    }

    /**
     * Muestra la ventana en pantalla.
     * Debe centrarla y hacerla visible.
     */
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
        txtUsuario.requestFocus();
    }

    /**
     * Maneja el evento de login al presionar el botón.
     * Debe validar credenciales y abrir la siguiente ventana o mostrar error.
     */
    private void login() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Por favor, complete todos los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreUsuario = validarCredenciales(usuario, clave);

        if (!nombreUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "¡Bienvenido " + nombreUsuario + "!",
                    "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);

            frame.dispose(); // Cerrar ventana de login
            new VentanaRuleta(nombreUsuario).mostrarVentana();
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Usuario o contraseña incorrectos.",
                    "Error de Login", JOptionPane.ERROR_MESSAGE);
            txtClave.setText("");
            txtUsuario.requestFocus();
        }
    }

    /**
     * Valida las credenciales del usuario contra el arreglo hardcoded.
     *
     * @param u nombre de usuario ingresado
     * @param p clave ingresada
     * @return el nombre del usuario si es válido, o cadena vacía si no coincide
     */
    private String validarCredenciales(String u, String p) {
        for (Usuario usuario : USUARIOS) {
            if (usuario.validarCredenciales(u, p)) {
                return usuario.getNombre();
            }
        }
        return "";
    }

    /**
     * Abre la ventana de registro para crear un nuevo usuario.
     * Debe cerrar la ventana actual e invocar a VentanaRegistro.
     */
    private void abrirRegistro() {
        frame.dispose();
        new VentanaRegistro().mostrarVentana();
    }

    // Método main para pruebas independientes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaLogin().mostrarVentana();
        });
    }
}
