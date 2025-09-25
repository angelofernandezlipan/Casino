import java.util.Random;
import java.util.Arrays;

public class Ruleta {
    // Uso de final para constantes
    public static final int MAX_HISTORIAL = 100;
    private final int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private final Random rng = new Random();

    // Arrays para el historial
    private int[] historialNumeros = new int[MAX_HISTORIAL];
    private int[] historialApuestas = new int[MAX_HISTORIAL];
    private boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    private int historialSize = 0;

    /**
     * Simula el giro de la ruleta generando un número aleatorio de 0 a 36.
     * @return El número de la ruleta.
     */
    public int girarRuleta() {
        return rng.nextInt(37); // Números de 0 a 36
    }

    /**
     * Evalúa si la apuesta fue acertada.
     * @param numero El número obtenido en la ruleta.
     * @param tipo El tipo de apuesta ('R' para rojo, 'N' para negro, 'P' para par, 'I' para impar).
     * @return true si acertó, false si perdió.
     */
    public boolean evaluarResultado(int numero, String tipoApuesta) {
        if (numero == 0) return false;

        switch (tipoApuesta.toLowerCase()) {
            case "rojo":
                return numerosRojos.contains(numero);
            case "negro":
                // Assuming you have a way to check for black numbers
                return !numerosRojos.contains(numero) && numero != 0;
            case "par":
                return numero % 2 == 0;
            case "impar":
                return numero % 2 != 0;
            default:
                return false;
        }
    }
    /**
     * Determina si un número es rojo.
     * @param n El número de la ruleta.
     * @return true si es rojo, false en caso contrario.
     */
    private boolean esRojo(int n) {
        return Arrays.binarySearch(numerosRojos, n) >= 0;
    }

    /**
     * Registra los resultados de la ronda.
     * @param numero El número obtenido.
     * @param apuesta El monto apostado.
     * @param acierto Si el jugador acertó.
     */
    public void registrarResultado(int numero, int apuesta, boolean acierto) {
        if (historialSize < MAX_HISTORIAL) {
            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = apuesta;
            historialAciertos[historialSize] = acierto;
            historialSize++;
        }
    }
}
