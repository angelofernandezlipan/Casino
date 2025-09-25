import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenu extends JFrame {

    public VentanaMenu() {
        super("Menú Principal - Casino Black Cat");

        JButton btnJugar = new JButton("Jugar Ruleta");
        JButton btnHistorial = new JButton("Historial");
        JButton btnSalir = new JButton("Cerrar Sesión");

        btnJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Abrir la ventana de la ruleta
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra esta ventana
                new VentanaLogin(); // Regresa a la ventana de login
            }
        });

        // TODO: Configurar layout y agregar componentes
        setVisible(true);
    }
}