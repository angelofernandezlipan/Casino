package modelo;

import java.util.List;

public class Estadisticas {

    private final List<Resultado> historial;

    // Constructor que recibe la lista del usuario
    public Estadisticas(List<Resultado> historial) {
        this.historial = historial;
    }

    public int getTotalJugadas() {
        return historial.size(); [cite_start]// total Jugadas [cite: 20]
    }

    public int getTotalAciertos() {
        // Cuenta los resultados donde el booleano 'acierto' es verdadero
        return (int) historial.stream()
                .filter(Resultado::isAcierto)
                .count(); [cite_start]// victorias [cite: 21]
    }

    public double getPorcentajeAciertos() {
        int total = getTotalJugadas();
        if (total == 0) return 0.0;

        [cite_start]// porcentajeVictorias [cite: 21]
        return (double) getTotalAciertos() / total * 100.0;
    }

    public int getGananciaNeta() {
        int ganancia = 0;

        for (Resultado r : historial) {
            int monto = r.getMontoApostado();
            if (r.isAcierto()) {
                ganancia += monto;
            } else {
                ganancia -= monto;
            }
        }
        return ganancia;
    }

    // NOTA: rachaMaxima y tipoMasJugado son más complejos y no están implementados aquí (V6)
}