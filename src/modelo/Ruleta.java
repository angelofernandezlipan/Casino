package modelo;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Ruleta {
    public static final int MAX_HISTORIAL = 100;
    // Se usa una lista para poder usar el método .contains()
    private final List<Integer> numerosRojos = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
    private final Random rng = new Random();

    // Arrays para el historial
    private int[] historialNumeros = new int[MAX_HISTORIAL];
    private String[] historialApuestas = new String[MAX_HISTORIAL];
    private boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    private int historialSize = 0;

    public int girarRuleta() {
        return rng.nextInt(37); // Números de 0 a 36 [cite: 201]
    }

    public boolean evaluarResultado(int numero, String tipoApuesta) {
        if (numero == 0) return false;

        return switch (tipoApuesta) {
            case "Rojo" -> numerosRojos.contains(numero);
            case "Negro" -> !numerosRojos.contains(numero) && numero != 0;
            case "Par" -> numero % 2 == 0;
            case "Impar" -> numero % 2 != 0;
            default -> false;
        };
    }

    public void registrarResultado(int numero, String tipoApuesta, boolean acierto) {
        if (historialSize < MAX_HISTORIAL) {
            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = tipoApuesta;
            historialAciertos[historialSize] = acierto;
            historialSize++;
        }
    }
}