import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la ventana de registro de nuevos usuarios.
 * Permite crear una cuenta nueva en el Casino Black Cat.
 */
public class VentanaRegistro {
    private final JFrame frame = new JFrame("Registro - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Contraseña:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JLabel lblNombre = new JLabel("Nombre completo:");
    private final JTextField txtNombre = new JTextField();
    private final JButton btnRegistrar = new JButton("Crear cuenta");
    private final JButton btnVolver = new JButton("Volver al login");

    /**
     * Constructor que inicializa la ventana de registro.
     */
    public VentanaRegistro() {
        configurarVentana();
        configurarEventos();
    }

    /**
     * Configura la ventana y sus componentes.
     */
    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titulo = new JLabel("CREAR CUENTA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        frame.add(titulo, gbc);

        // Usuario
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(lblUsuario, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(txtUsuario, gbc);

        // Contraseña
        gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(lblClave, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(txtClave, gbc);

        // Nombre
        gbc.anchor = GridBagConstraints.EAST; gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(lblNombre, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(txtNombre, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(btnRegistrar, gbc);
        gbc.gridx = 1;
        frame.add(btnVolver, gbc);
    }

    /**
     * Configura los eventos de los botones.
     */
    private void configurarEventos() {
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnVolver.addActionListener(e -> volverAlLogin());
    }

    /**
     * Muestra la ventana en pantalla centrada.
     */
    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        txtUsuario.requestFocus();
    }

    /**
     * Maneja el registro de un nuevo usuario.
     */
    private void registrarUsuario() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());
        String nombre = txtNombre.getText().trim();

        // Validar campos completos
        if (usuario.isEmpty() || clave.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Por favor, complete todos los campos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el usuario no exista
        if (usuarioExiste(usuario)) {
            JOptionPane.showMessageDialog(frame,
                    "El nombre de usuario ya existe. Elija otro.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtUsuario.requestFocus();
            return;
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario(usuario, clave, nombre);
        VentanaLogin.USUARIOS.add(nuevoUsuario);

        // Mostrar confirmación
        JOptionPane.showMessageDialog(frame,
                "Usuario registrado exitosamente.\n¡Ahora puede iniciar sesión!",
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

        // Volver al login
        volverAlLogin();
    }

    /**
     * Verifica si un nombre de usuario ya existe.
     */
    private boolean usuarioExiste(String username) {
        return VentanaLogin.USUARIOS.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    /**
     * Regresa a la ventana de login.
     */
    private void volverAlLogin() {
        frame.dispose();
        new VentanaLogin().mostrarVentana();
    }
}