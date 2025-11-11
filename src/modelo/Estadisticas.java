package modelo;

import java.util.List;

public class Estadisticas {

    private final IRepositorioResultados repositorio;

    // Constructor que recibe la lista del usuario (En la V8 SE CAMBIÓ EL PARÁMETRO)
    public Estadisticas(IRepositorioResultados repositorio) {
        this.repositorio = repositorio;
    }

    private List<Resultado> getHistorial() {
        return repositorio.obtenerTodos();
    }

    public int getTotalJugadas() {
        return getHistorial().size();
    }

    public int getTotalAciertos() {
        // Cuenta los resultados donde el booleano 'acierto' es verdadero
        return (int) getHistorial().stream()
                .filter(Resultado::isAcierto)
                .count();
    }

    public double getPorcentajeAciertos() {
        int total = getTotalJugadas();
        if (total == 0) return 0.0;
        return (double) getTotalAciertos() / total * 100.0;
    }

    public int getGananciaNeta() {
        int ganancia = 0;

        for (Resultado r : getHistorial()) {
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