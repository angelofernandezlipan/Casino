package modelo;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Ruleta {

    // Lógica pura de juego (sin atributos de historial)
    private final List<Integer> numerosRojos = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
    private final Random rng = new Random();

    public int girarRuleta() {
        return rng.nextInt(37);
    }

    public String colorDe(int numero) {
        if (numero == 0) {
            return "Verde"; // 0 es Verde
        }
        return numerosRojos.contains(numero) ? "Rojo" : "Negro";
    }

    // NO CONTIENE registrarResultado() ni arrays de historial (Versión 5)
    // Se eliminó evaluarResultado(int número, TipoApuesta tipoApuesta)
    // Su lógica ahora está distribuida en las subclases de ApuestaBase GRACIAS A QUE DESCUBRIMOS LA HERENCIA (V7).
}