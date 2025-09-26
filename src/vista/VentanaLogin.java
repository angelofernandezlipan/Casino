package vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin extends JFrame {

    private final List<Usuario> usuarios = new ArrayList<>();
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar; // <--- Declaración como atributo de la clase
    private JButton btnRegistrar; // <--- Declaración como atributo de la clase

    public VentanaLogin() {
        super("Login - Casino Black Cat");
        usuarios.add(new Usuario("donnie", "1234", "Donnie"));
        usuarios.add(new Usuario("admin", "admin", "Administrador"));

        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        txtUsuario = new JTextField(15);
        txtClave = new JPasswordField(15);
        btnIngresar = new JButton("Ingresar");
        btnRegistrar = new JButton("Registrar");

        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad de registro en desarrollo."));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelForm.add(new JLabel("Usuario:"));
        panelForm.add(txtUsuario);
        panelForm.add(new JLabel("Clave:"));
        panelForm.add(txtClave);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnRegistrar); // <-- Se agrega la variable
        panelBotones.add(btnIngresar);  // <-- Se agrega la variable

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void login() {
        String user = txtUsuario.getText();
        String pass = new String(txtClave.getPassword());

        Usuario usuarioLogeado = validarCredenciales(user, pass);
        if (usuarioLogeado != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usuarioLogeado.getNombre() + "!");
            new VentanaMenu(usuarioLogeado.getNombre());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Usuario validarCredenciales(String u, String p) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(u, p)) {
                return usuario;
            }
        }
        return null;
    }
}