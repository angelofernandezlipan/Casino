import java.util.Random;
import java.util.Scanner;

public class Ruleta {
    public static final int MAX_HISTORIAL = 100;
    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;
    public static Random rng = new Random();
    public static int[] numerosRojos = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};

    /**
     * Método principal: inicia el programa llamando al menú.
     */
    public static void main(String[] args) {
        menu();
    }

    /**
     * Controla el flujo principal del programa mostrando un menú en consola.
     */
    public static void menu() {
        Scanner in = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion(in);
            ejecutarOpcion(opcion, in);
        } while (opcion != 3);

        in.close();
    }

    /**
     * Muestra en consola las opciones disponibles del menú.
     */
    public static void mostrarMenu() {
        System.out.println("\n=== CASINO BLACK CAT - RULETA ===");
        System.out.println("1. Iniciar ronda de ruleta");
        System.out.println("2. Ver estadísticas");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }

    /**
     * Lee la opción elegida por el usuario desde teclado.
     * @param in Scanner para entrada por consola.
     * @return número de opción ingresado.
     */
    public static int leerOpcion(Scanner in) {
        try {
            return in.nextInt();
        } catch (Exception e) {
            in.nextLine(); // Limpiar buffer
            return 0; // Opción inválida
        }
    }

    /**
     * Ejecuta la acción correspondiente a la opción del menú.
     * @param opcion opción elegida por el usuario.
     * @param in Scanner para entrada por consola.
     */
    public static void ejecutarOpcion(int opcion, Scanner in) {
        switch (opcion) {
            case 1:
                iniciarRonda(in);
                break;
            case 2:
                mostrarEstadisticas();
                break;
            case 3:
                System.out.println("¡Gracias por jugar en Casino Black Cat!");
                break;
            default:
                System.out.println("Opción inválida. Intenta nuevamente.");
        }
    }

    /**
     * Inicia una ronda de la ruleta: leer apuesta, girar, evaluar y mostrar resultado.
     * @param in Scanner para entrada por consola.
     */
    public static void iniciarRonda(Scanner in) {
        char tipoApuesta = leerTipoApuesta(in);
        System.out.print("Ingresa el monto de la apuesta: $");
        int monto = in.nextInt();

        int numero = girarRuleta();
        boolean acierto = evaluarResultado(numero, tipoApuesta);

        registrarResultado(numero, monto, acierto);
        mostrarResultado(numero, tipoApuesta, monto, acierto);
    }

    /**
     * Permite al usuario seleccionar el tipo de apuesta (R/N/P/I).
     * @param in Scanner para entrada por consola.
     * @return el tipo de apuesta elegido.
     */
    public static char leerTipoApuesta(Scanner in) {
        System.out.println("Tipos de apuesta:");
        System.out.println("R - Rojo | N - Negro | P - Par | I - Impar");
        System.out.print("Elige tu apuesta: ");

        String entrada = in.next().toUpperCase();
        return entrada.charAt(0);
    }

    /**
     * Simula el giro de la ruleta generando un número aleatorio de 0 a 36.
     * @return número de la ruleta.
     */
    public static int girarRuleta() {
        System.out.println("Girando la ruleta...");
        try {
            Thread.sleep(1000); // Pausa dramática
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return rng.nextInt(37); // 0 a 36
    }

    /**
     * Evalúa si la apuesta realizada por el jugador fue acertada.
     * @param numero número obtenido en la ruleta.
     * @param tipo tipo de apuesta elegida.
     * @return true si acertó, false si perdió.
     */
    public static boolean evaluarResultado(int numero, char tipo) {
        switch (tipo) {
            case 'R':
                return esRojo(numero);
            case 'N':
                return !esRojo(numero) && numero != 0;
            case 'P':
                return numero % 2 == 0 && numero != 0;
            case 'I':
                return numero % 2 == 1;
            default:
                return false;
        }
    }

    /**
     * Determina si un número corresponde a color rojo.
     * @param n número de la ruleta.
     * @return true si es rojo, false en caso contrario.
     */
    public static boolean esRojo(int n) {
        for (int numeroRojo : numerosRojos) {
            if (n == numeroRojo) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra los resultados de la ronda en los arreglos de historial.
     * @param numero número obtenido en la ruleta.
     * @param apuesta monto apostado.
     * @param acierto si el jugador acertó o no.
     */
    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        if (historialSize < MAX_HISTORIAL) {
            historialNumeros[historialSize] = numero;
            historialApuestas[historialSize] = apuesta;
            historialAciertos[historialSize] = acierto;
            historialSize++;
        }
    }

    /**
     * Muestra en consola el resultado de la ronda.
     * @param numero número obtenido en la ruleta.
     * @param tipo tipo de apuesta realizada.
     * @param monto monto apostado.
     * @param acierto si el jugador ganó o perdió.
     */
    public static void mostrarResultado(int numero, char tipo, int monto, boolean acierto) {
        String color = esRojo(numero) ? "ROJO" : (numero == 0 ? "VERDE" : "NEGRO");
        String paridad = numero == 0 ? "CERO" : (numero % 2 == 0 ? "PAR" : "IMPAR");

        System.out.println("\n=== RESULTADO ===");
        System.out.println("Número: " + numero + " (" + color + ", " + paridad + ")");

        if (acierto) {
            System.out.println("¡GANASTE! +$" + monto);
        } else {
            System.out.println("Perdiste. -$" + monto);
        }
    }

    /**
     * Muestra estadísticas generales de todas las rondas jugadas.
     */
    public static void mostrarEstadisticas() {
        if (historialSize == 0) {
            System.out.println("No hay estadísticas disponibles. ¡Juega una ronda!");
            return;
        }

        int totalApostado = 0;
        int aciertos = 0;
        int gananciaNeta = 0;

        for (int i = 0; i < historialSize; i++) {
            totalApostado += historialApuestas[i];
            if (historialAciertos[i]) {
                aciertos++;
                gananciaNeta += historialApuestas[i];
            } else {
                gananciaNeta -= historialApuestas[i];
            }
        }

        double porcentajeAcierto = (double) aciertos / historialSize * 100;

        System.out.println("\n=== ESTADÍSTICAS ===");
        System.out.println("Rondas jugadas: " + historialSize);
        System.out.println("Total apostado: $" + totalApostado);
        System.out.println("Total aciertos: " + aciertos);
        System.out.printf("Porcentaje de acierto: %.2f%%\n", porcentajeAcierto);
        System.out.println("Ganancia/Pérdida neta: $" + gananciaNeta);
    }
}
