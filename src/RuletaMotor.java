import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ESTA CLASE ES EL MOTOR DE LA RULETA, SEPARADA DE LA INTERFAZ DE USUARIO
 */

public class RuletaMotor {
    protected static final int[] NUMEROS_ROJOS = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
    private final Random rng = new Random();

    private final List<Integer> historialNumeros = new ArrayList<>();
    private final List<Integer> historialApuestas = new ArrayList<>();
    private final List<Boolean> historialAciertos = new ArrayList<>();

    public int girar() {
        return rng.nextInt(37); // 0 a 36
    }

    public boolean evaluarApuesta(int numero, char tipoApuesta) {
        switch (tipoApuesta) {
            case 'R': return esRojo(numero);
            case 'N': return !esRojo(numero) && numero != 0;
            case 'P': return numero % 2 == 0 && numero != 0;
            case 'I': return numero % 2 == 1;
            default: return false;
        }
    }

    private boolean esRojo(int numero) {
        for (int numRojo : NUMEROS_ROJOS) {
            if (numero == numRojo) return true;
        }
        return false;
    }

    public void registrarJugada(int numero, int apuesta, boolean acierto) {
        historialNumeros.add(numero);
        historialApuestas.add(apuesta);
        historialAciertos.add(acierto);
    }

    public int getTotalRondas() {
        return historialNumeros.size();
    }

    public int getTotalApostado() {
        return historialApuestas.stream().mapToInt(Integer::intValue).sum();
    }

    public int getAciertos() {
        return (int) historialAciertos.stream().filter(b -> b).count();
    }

    public double getPorcentajeAciertos() {
        if (historialAciertos.isEmpty()) return 0;
        return (double) getAciertos() / getTotalRondas() * 100;
    }

    public int getGananciaNeta() {
        int ganancia = 0;
        for (int i = 0; i < historialAciertos.size(); i++) {
            if (historialAciertos.get(i)) {
                ganancia += historialApuestas.get(i);
            } else {
                ganancia -= historialApuestas.get(i);
            }
        }
        return ganancia;
    }
}
