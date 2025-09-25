import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin extends JFrame {

    private final List<Usuario> usuarios = new ArrayList<>();
    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public VentanaLogin() {
        super("Login - Casino Black Cat");

        // Hardcodeamos usuarios para el prototipo
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));

        initComponents();
        setupLayout();
        setVisible(true);
    }

    private void initComponents() {
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblClave = new JLabel("Clave:");
        txtUsuario = new JTextField();
        txtClave = new JPasswordField();
        JButton btnIngresar = new JButton("Ingresar");
        JButton btnRegistrar = new JButton("Registrar");

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });

        // TODO: Agregar el resto de componentes y configurar el layout
    }

    private void setupLayout() {
        // TODO: Configurar el layout de la ventana
    }

    private void login() {
        String user = txtUsuario.getText();
        String pass = new String(txtClave.getPassword());

        String nombreUsuario = validarCredenciales(user, pass);
        if (!nombreUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + nombreUsuario + "!");
            // TODO: Crear e instanciar VentanaMenu
            dispose(); // Cierra la ventana actual
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validarCredenciales(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                return usuario.getNombre();
            }
        }
        return "";
    }

    private void abrirRegistro() {
        // TODO: Implementar la ventana de registro
    }
}