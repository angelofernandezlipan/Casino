package vista;

import javax.swing.*;
import java.awt.*;
import modelo.Estadisticas;
import modelo.IRepositorioResultados; // V8
import controlador.SessionController;

public class VentanaEstadisticas extends JFrame {

    public VentanaEstadisticas() {
        super("Estadísticas del Jugador");

        // V8: Obtiene el repositorio del controlador
        IRepositorioResultados repo = SessionController.getInstance().getRepositorio();

        if (repo == null) {
            JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el repositorio de historial.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // V8: El modelo Estadisticas ahora recibe el Repositorio
        Estadisticas stats = new Estadisticas(repo);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Muestra las métricas calculadas
        panel.add(new JLabel("Jugadas Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getTotalJugadas())));

        panel.add(new JLabel("Aciertos Totales:"));
        panel.add(new JLabel(String.valueOf(stats.getTotalAciertos())));

        panel.add(new JLabel("Porcentaje de Aciertos:"));
        panel.add(new JLabel(String.format("%.2f %%", stats.getPorcentajeAciertos())));

        panel.add(new JLabel("Ganancia Neta:"));
        panel.add(new JLabel(String.format("$%d", stats.getGananciaNeta())));

        add(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}