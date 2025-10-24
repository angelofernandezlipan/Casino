package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Estadisticas;
import modelo.Usuario;
import controlador.SessionController;

public class VentanaEstadisticas extends JFrame {

    public VentanaEstadisticas() {
        super("Estadísticas del Jugador");

        Usuario usuarioActual = SessionController.getInstance().getUsuarioActual();
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión para ver las estadísticas.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Crear el Modelo Estadisticas con el historial del usuario (V6)
        Estadisticas stats = new Estadisticas(usuarioActual.getHistorial());

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //[cite_start]// Mostrar las métricas calculadas [cite: 17, 19]
        panel.add(new JLabel("Jugadas Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getTotalJugadas())));

        panel.add(new JLabel("Aciertos Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getTotalAciertos())));

        panel.add(new JLabel("Porcentaje de Aciertos:"));
        panel.add(new JLabel(String.format("%.2f %%", stats.getPorcentajeAciertos())));

        panel.add(new JLabel("Ganancia Neta:"));
        panel.add(new JLabel(String.format("$%d", stats.getGananciaNeta())));

        //[cite_start]// NOTA: Métricas como rachaMaxima y tipoMasJugado requieren lógica adicional [cite: 22, 23]

        add(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}