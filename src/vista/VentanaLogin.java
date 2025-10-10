package vista;

import controlador.SessionController;
import javax.swing.*;
import java.awt.*;
import controlador.SessionController; // Importar el controlador
import modelo.Usuario; // Importar el modelo

public class VentanaLogin extends JFrame {

    // Variables declaradas a nivel de CLASE (para corregir el error de alcance)
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private JButton btnRegistrar;

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
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnIngresar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void login() {
        String user = txtUsuario.getText();
        String pass = new String(txtClave.getPassword());

        // La VISTA llama al CONTROLADOR
        SessionController controller = SessionController.getInstance();
        Usuario usuarioLogeado = controller.iniciarSesion(user, pass);

        if (usuarioLogeado != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usuarioLogeado.getNombre() + "!");
            new VentanaMenu(usuarioLogeado.getNombre());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // IMPORTANTE: Eliminar la lista de usuarios y el méthod validarCredenciales() de esta clase.
}